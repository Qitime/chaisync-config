#!/bin/sh
# 拦住明文凭据进版本库。
#
# 这是整套流程里最值得装的一道防护：加密做得再好，也管不到"你自己把明文提交进去"。
# 一个 commit 里有明文 accessToken，push 之后就永久留在历史里，force push 也删不干净。
#
# 安装：
#   cp tools/precommit.sh .git/hooks/pre-commit && chmod +x .git/hooks/pre-commit
#
# 例外：示例文件里的占位符（PUT_REAL / CHANGE_ME / <...>）放行。

status=0

if git diff --cached -U0 \
    | grep -E '^\+' \
    | grep -vE 'PUT_REAL|CHANGE_ME|YOUR_|<[A-Z_]+>' \
    | grep -nE '"(accessToken|password)"[[:space:]]*:[[:space:]]*"[A-Za-z0-9_./+-]{16,}"'; then
    echo ""
    echo "REFUSED: staged diff contains a plaintext credential."
    echo "  real values belong in config/config.json, which is gitignored."
    status=1
fi

if git diff --cached --name-only | grep -qE '^(keys/|config/config\.json$)'; then
    echo ""
    echo "REFUSED: you are staging a secret file (keys/ or config/config.json)."
    status=1
fi

if [ "$status" -ne 0 ]; then
    echo ""
    echo "commit aborted. use 'git commit --no-verify' only if you are certain."
fi

exit $status
