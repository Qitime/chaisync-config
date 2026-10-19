package defpackage;

import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.automirrored.filled.ArrowBackKt;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.badlogic.gdx.net.HttpStatus;
import com.dh.myembyapp.data.model.ServerConfig;
import com.dh.myembyapp.data.model.ServerPingState;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: D:\Ai\Github\ChaiSync-GH\build\decoded\classes4.dex */
public abstract class r02 {
    public static final List a(List list, String str, boolean z, boolean z2, Map map, String str2) {
        list.getClass();
        map.getClass();
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        listCreateListBuilder.add(new vo0("__server_list__", "返回服务器列表页", str2, false, false, ArrowBackKt.getArrowBack(Icons.AutoMirrored.Filled.INSTANCE), null, null, null, null, 2000));
        if (z) {
            listCreateListBuilder.add(new vo0("aggregate_search", "聚合视界", TtmlNode.ANONYMOUS_REGION_ID, z2, false, null, null, Integer.valueOf(qt1.chaichai), null, null, 1712));
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ServerConfig serverConfig = (ServerConfig) it.next();
            ServerConfig effectiveServerConfig = serverConfig.getEffectiveServerConfig();
            String strX = serverConfig.getActiveBackupRouteId() != null ? bc0.x(" · ", serverConfig.getCurrentRouteDisplayName()) : null;
            String str3 = TtmlNode.ANONYMOUS_REGION_ID;
            if (strX == null) {
                strX = TtmlNode.ANONYMOUS_REGION_ID;
            }
            if (!serverConfig.isLoggedIn()) {
                str3 = " · 需重新登录";
            }
            listCreateListBuilder.add(new vo0(serverConfig.getId(), serverConfig.getAlias(), zr.C(effectiveServerConfig.getDisplayAddress(), strX, str3), !z2 && Intrinsics.areEqual(serverConfig.getId(), str), false, null, serverConfig.getCustomIconPath(), null, (ServerPingState) map.get(serverConfig.getId()), xu.C(serverConfig.getLastPlaybackAccessTime()), HttpStatus.SC_NOT_MODIFIED));
        }
        return CollectionsKt.build(listCreateListBuilder);
    }
}
