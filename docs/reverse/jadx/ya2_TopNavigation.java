package defpackage;

import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.FavoriteKt;
import androidx.compose.material.icons.filled.SearchKt;
import androidx.compose.material.icons.filled.SettingsKt;
import androidx.compose.material.icons.filled.VideoLibraryKt;
import androidx.compose.material.icons.filled.VpnKeyKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotMutationPolicy;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.focus.FocusChangedModifierKt;
import androidx.compose.ui.focus.FocusPropertiesKt;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.extractor.ts.PsExtractor;
import androidx.profileinstaller.ProfileVerifier;
import androidx.tv.material3.Border;
import androidx.tv.material3.ButtonBorder;
import androidx.tv.material3.ButtonColors;
import androidx.tv.material3.ButtonDefaults;
import androidx.tv.material3.ButtonGlow;
import androidx.tv.material3.ButtonKt;
import androidx.tv.material3.ButtonScale;
import androidx.tv.material3.ButtonShape;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: D:\Ai\Github\ChaiSync-GH\build\decoded\classes4.dex */
public abstract class ya2 {
    public static final float a = Dp.constructor-impl(85.0f);
    public static final float b = Dp.constructor-impl(250.0f);
    public static final long c = ColorKt.Color(4282622023L);
    public static final long d = ColorKt.Color(4288323750L);

    public static final void a(final String str, final ImageVector imageVector, final boolean z, final to0 to0Var, final to0 to0Var2, final Function1 function1, Function0 function0, final boolean z2, final boolean z3, final FocusRequester focusRequester, final boolean z4, Modifier modifier, Composer composer, final int i, final int i2, final int i3) {
        int i4;
        Function1 function2;
        Function0 function3;
        int i5;
        Composer composer2;
        final Function0 function4;
        final Modifier modifier2;
        Object e6Var;
        MutableState mutableState;
        long j;
        long j2;
        long jU;
        Composer composerStartRestartGroup = composer.startRestartGroup(-176590822);
        if ((i & 6) == 0) {
            i4 = (composerStartRestartGroup.changed(str) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i & 48) == 0) {
            i4 |= composerStartRestartGroup.changed(imageVector) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i4 |= composerStartRestartGroup.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i4 |= composerStartRestartGroup.changed(to0Var.ordinal()) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i4 |= composerStartRestartGroup.changed(to0Var2.ordinal()) ? GL20.GL_COLOR_BUFFER_BIT : 8192;
        }
        if ((196608 & i) == 0) {
            function2 = function1;
            i4 |= composerStartRestartGroup.changedInstance(function2) ? ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ : 65536;
        } else {
            function2 = function1;
        }
        int i6 = i3 & 64;
        if (i6 != 0) {
            i4 |= 1572864;
            function3 = function0;
        } else {
            function3 = function0;
            if ((i & 1572864) == 0) {
                i4 |= composerStartRestartGroup.changedInstance(function3) ? ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES : 524288;
            }
        }
        if ((i & 12582912) == 0) {
            i4 |= composerStartRestartGroup.changed(z2) ? 8388608 : 4194304;
        }
        if ((i & 100663296) == 0) {
            i4 |= composerStartRestartGroup.changed(z3) ? 67108864 : 33554432;
        }
        if ((i & 805306368) == 0) {
            i4 |= composerStartRestartGroup.changed(focusRequester) ? 536870912 : 268435456;
        }
        if ((i2 & 6) == 0) {
            i5 = i2 | (composerStartRestartGroup.changed(z4) ? 4 : 2);
        } else {
            i5 = i2;
        }
        int i7 = i5 | 432;
        if (composerStartRestartGroup.shouldExecute(((i4 & 306783379) == 306783378 && (i7 & 147) == 146) ? false : true, i4 & 1)) {
            Function0 function5 = i6 != 0 ? null : function3;
            Modifier modifier3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-176590822, i4, i7, "com.dh.myembyapp.ui.screens.home.NavButton (TopNavigation.kt:310)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1159792958, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-accentFollowsTheme> (Theme.kt:147)");
            }
            boolean zBooleanValue = ((Boolean) composerStartRestartGroup.consume(k92.g)).booleanValue();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            boolean z5 = !zBooleanValue;
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default);
                objRememberedValue = mutableStateMutableStateOf$default;
            }
            MutableState mutableState2 = (MutableState) objRememberedValue;
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue2 == companion.getEmpty()) {
                objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            MutableState mutableState3 = (MutableState) objRememberedValue2;
            Boolean bool = (Boolean) mutableState2.getValue();
            bool.getClass();
            Boolean boolValueOf = Boolean.valueOf(z4);
            Function0 function6 = function5;
            int i8 = 57344 & i4;
            int i9 = i4 & 7168;
            int i10 = i4;
            boolean z6 = ((i7 & 14) == 4) | ((29360128 & i4) == 8388608) | (i8 == 16384) | (i9 == 2048);
            int i11 = i10 & 458752;
            boolean z7 = z6 | (i11 == 131072);
            Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (z7 || objRememberedValue3 == companion.getEmpty()) {
                e6Var = new e6(z4, z2, to0Var2, to0Var, function2, mutableState2, mutableState3, null, 2);
                mutableState = mutableState2;
                composerStartRestartGroup.updateRememberedValue(e6Var);
            } else {
                e6Var = objRememberedValue3;
                mutableState = mutableState2;
            }
            EffectsKt.LaunchedEffect(bool, boolValueOf, (Function2) e6Var, composerStartRestartGroup, (i7 << 3) & 112);
            boolean z8 = to0Var == to0Var2 || z4;
            if (z3) {
                j = Color.Companion.getTransparent-0d7_KjU();
            } else {
                j = z ? g81Var.I : g81Var.G;
            }
            Modifier modifierFocusRequester = FocusRequesterModifierKt.focusRequester(SizeKt.width-3ABfNKs(SizeKt.height-3ABfNKs(modifier3, Dp.constructor-impl(40.0f)), Dp.constructor-impl(85.0f)), focusRequester);
            Object objRememberedValue4 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue4 == companion.getEmpty()) {
                objRememberedValue4 = new pz1(12, mutableState);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue4);
            }
            Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifierFocusRequester, (Function1) objRememberedValue4);
            boolean z9 = (i7 & 112) == 32;
            Object objRememberedValue5 = composerStartRestartGroup.rememberedValue();
            if (z9 || objRememberedValue5 == companion.getEmpty()) {
                objRememberedValue5 = new wa2();
                composerStartRestartGroup.updateRememberedValue(objRememberedValue5);
            }
            Modifier modifierOnPreviewKeyEvent = KeyInputModifierKt.onPreviewKeyEvent(modifierOnFocusChanged, (Function1) objRememberedValue5);
            ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
            if (!z8) {
                j2 = j;
            } else if (zBooleanValue) {
                j2 = z ? g81Var.J : g81Var.H;
            } else {
                j2 = Color.Companion.getWhite-0d7_KjU();
            }
            MaterialTheme materialTheme = MaterialTheme.INSTANCE;
            int i12 = MaterialTheme.$stable;
            long j3 = materialTheme.getColorScheme(composerStartRestartGroup, i12).getOnSurface-0d7_KjU();
            if (zBooleanValue) {
                jU = bc0.u(composerStartRestartGroup, 2095016803, materialTheme, composerStartRestartGroup, i12);
            } else {
                composerStartRestartGroup.startReplaceGroup(2095015243);
                composerStartRestartGroup.endReplaceGroup();
                jU = ColorKt.Color(4279308561L);
            }
            int i13 = ButtonDefaults.$stable;
            MutableState mutableState4 = mutableState;
            ButtonColors buttonColorsM345colorsoq7We08 = buttonDefaults.m345colorsoq7We08(j, j3, j2, jU, 0L, 0L, 0L, 0L, composerStartRestartGroup, i13 << 24, PsExtractor.VIDEO_STREAM_MASK);
            ButtonScale buttonScaleScale$default = ButtonDefaults.scale$default(buttonDefaults, 0.0f, z8 ? 1.05f : 1.0f, 0.0f, 0.0f, 0.0f, 29, null);
            ButtonGlow buttonGlowA = z8 ? im0.a(g81Var) : ButtonDefaults.glow$default(buttonDefaults, null, null, null, 7, null);
            ButtonBorder buttonBorderBorder = buttonDefaults.border(null, z8 ? new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null) : new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(0.0f), Color.Companion.getTransparent-0d7_KjU()), 0.0f, null, 6, null), null, null, null, composerStartRestartGroup, i13 << 15, 29);
            PaddingValues paddingValues = PaddingKt.PaddingValues-YgX7TsA(Dp.constructor-impl(12.0f), Dp.constructor-impl(8.0f));
            boolean z10 = (i8 == 16384) | (i9 == 2048) | ((i10 & 3670016) == 1048576) | (i11 == 131072);
            Object objRememberedValue6 = composerStartRestartGroup.rememberedValue();
            if (z10 || objRememberedValue6 == companion.getEmpty()) {
                objRememberedValue6 = new nh(17, to0Var2, to0Var, function6, function1);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
            }
            composer2 = composerStartRestartGroup;
            ButtonKt.m348ButtonTCVpFMg((Function0) objRememberedValue6, modifierOnPreviewKeyEvent, null, false, buttonScaleScale$default, buttonGlowA, null, buttonColorsM345colorsoq7We08, 0.0f, buttonBorderBorder, paddingValues, null, ComposableLambdaKt.rememberComposableLambda(-2055275478, true, new c5(imageVector, z8, z5, z, str, mutableState4, 1), composerStartRestartGroup, 54), composer2, 0, 390, 2380);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = modifier3;
            function4 = function6;
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
            function4 = function3;
            modifier2 = modifier;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: va2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i2);
                    ya2.a(str, imageVector, z, to0Var, to0Var2, function1, function4, z2, z3, focusRequester, z4, modifier2, (Composer) obj, iUpdateChangedFlags, iUpdateChangedFlags2, i3);
                    return Unit.INSTANCE;
                }
            });
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0131  */
    /* JADX WARN: Code duplicated, block: B:107:0x014b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:108:0x014d  */
    /* JADX WARN: Code duplicated, block: B:110:0x0150  */
    /* JADX WARN: Code duplicated, block: B:113:0x0155  */
    /* JADX WARN: Code duplicated, block: B:114:0x0159  */
    /* JADX WARN: Code duplicated, block: B:116:0x015d  */
    /* JADX WARN: Code duplicated, block: B:118:0x0169  */
    /* JADX WARN: Code duplicated, block: B:120:0x0175  */
    /* JADX WARN: Code duplicated, block: B:124:0x0187  */
    /* JADX WARN: Code duplicated, block: B:127:0x0197  */
    /* JADX WARN: Code duplicated, block: B:130:0x01ad  */
    /* JADX WARN: Code duplicated, block: B:133:0x01b6  */
    /* JADX WARN: Code duplicated, block: B:136:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:139:0x01e3  */
    /* JADX WARN: Code duplicated, block: B:142:0x01fa  */
    /* JADX WARN: Code duplicated, block: B:143:0x0206  */
    /* JADX WARN: Code duplicated, block: B:146:0x0216  */
    /* JADX WARN: Code duplicated, block: B:147:0x0221  */
    /* JADX WARN: Code duplicated, block: B:150:0x022e  */
    /* JADX WARN: Code duplicated, block: B:151:0x023c  */
    /* JADX WARN: Code duplicated, block: B:154:0x024c  */
    /* JADX WARN: Code duplicated, block: B:157:0x0258  */
    /* JADX WARN: Code duplicated, block: B:159:0x0261  */
    /* JADX WARN: Code duplicated, block: B:162:0x0272  */
    /* JADX WARN: Code duplicated, block: B:165:0x0296  */
    /* JADX WARN: Code duplicated, block: B:166:0x029d  */
    /* JADX WARN: Code duplicated, block: B:169:0x02ab  */
    /* JADX WARN: Code duplicated, block: B:170:0x02ad  */
    /* JADX WARN: Code duplicated, block: B:173:0x02b5  */
    /* JADX WARN: Code duplicated, block: B:177:0x02c7  */
    /* JADX WARN: Code duplicated, block: B:180:0x02f2  */
    /* JADX WARN: Code duplicated, block: B:181:0x02f4  */
    /* JADX WARN: Code duplicated, block: B:184:0x0305  */
    /* JADX WARN: Code duplicated, block: B:186:0x030b  */
    /* JADX WARN: Code duplicated, block: B:191:0x0319  */
    /* JADX WARN: Code duplicated, block: B:195:0x0324  */
    /* JADX WARN: Code duplicated, block: B:198:0x0340 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:199:0x0342  */
    /* JADX WARN: Code duplicated, block: B:201:0x034b  */
    /* JADX WARN: Code duplicated, block: B:202:0x034e  */
    /* JADX WARN: Code duplicated, block: B:205:0x035e  */
    /* JADX WARN: Code duplicated, block: B:207:0x0373  */
    /* JADX WARN: Code duplicated, block: B:210:0x039f  */
    /* JADX WARN: Code duplicated, block: B:212:0x03a5  */
    /* JADX WARN: Code duplicated, block: B:215:0x03ba  */
    /* JADX WARN: Code duplicated, block: B:217:0x03c1  */
    /* JADX WARN: Code duplicated, block: B:219:0x03d2  */
    /* JADX WARN: Code duplicated, block: B:220:0x03ee  */
    /* JADX WARN: Code duplicated, block: B:223:0x0473  */
    /* JADX WARN: Code duplicated, block: B:225:0x047d  */
    /* JADX WARN: Code duplicated, block: B:228:0x0490  */
    /* JADX WARN: Code duplicated, block: B:230:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:31:0x0061  */
    /* JADX WARN: Code duplicated, block: B:33:0x0069  */
    /* JADX WARN: Code duplicated, block: B:34:0x006c  */
    /* JADX WARN: Code duplicated, block: B:36:0x0070  */
    /* JADX WARN: Code duplicated, block: B:39:0x0076  */
    /* JADX WARN: Code duplicated, block: B:41:0x007b  */
    /* JADX WARN: Code duplicated, block: B:43:0x007f  */
    /* JADX WARN: Code duplicated, block: B:45:0x0087  */
    /* JADX WARN: Code duplicated, block: B:46:0x008a  */
    /* JADX WARN: Code duplicated, block: B:50:0x0094  */
    /* JADX WARN: Code duplicated, block: B:52:0x009a  */
    /* JADX WARN: Code duplicated, block: B:55:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:57:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:60:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:62:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:63:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:67:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:69:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:70:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:74:0x00d6  */
    /* JADX WARN: Code duplicated, block: B:76:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:77:0x00df  */
    /* JADX WARN: Code duplicated, block: B:81:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:82:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:84:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:86:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:87:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:91:0x0113  */
    /* JADX WARN: Code duplicated, block: B:95:0x011c  */
    /* JADX WARN: Code duplicated, block: B:98:0x0125  */
    public static final void b(String str, String str2, Integer num, Function0 function0, Function0 function1, Function0 function2, boolean z, boolean z2, FocusRequester focusRequester, Function1 function3, Modifier modifier, Composer composer, int i, int i2, int i3) {
        int i4;
        Integer num2;
        Function0 function4;
        int i5;
        Function0 function5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z3;
        Composer composer2;
        Function0 function6;
        Modifier modifier2;
        Integer num3;
        Function0 function7;
        Function1 function8;
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup;
        Function0 function9;
        Function1 function10;
        Modifier modifier3;
        Function1 function11;
        Function0 function12;
        int i10;
        Object objRememberedValue;
        g81 g81Var;
        boolean zBooleanValue;
        Object objRememberedValue2;
        Composer.Companion companion;
        CoroutineScope coroutineScope;
        Object objRememberedValue3;
        SnapshotMutationPolicy snapshotMutationPolicy;
        MutableState mutableState;
        Object objRememberedValue4;
        MutableState mutableState2;
        Object objRememberedValue5;
        SnapshotMutationPolicy snapshotMutationPolicy2;
        MutableState mutableState3;
        Object objRememberedValue6;
        MutableState mutableState4;
        long j;
        long j2;
        Object objRememberedValue7;
        Modifier modifierFocusRequester;
        boolean z4;
        Object objRememberedValue8;
        MutableState mutableState5;
        MutableState mutableState6;
        MutableState mutableState7;
        boolean z5;
        boolean zChangedInstance;
        Object objRememberedValue9;
        Function0 function13;
        ButtonDefaults buttonDefaults;
        long j3;
        MaterialTheme materialTheme;
        int i11;
        long jColor;
        float f;
        ButtonGlow buttonGlowGlow$default;
        Border border;
        long j4;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        str.getClass();
        function0.getClass();
        Composer composerStartRestartGroup = composer.startRestartGroup(374056362);
        if ((i & 6) == 0) {
            i4 = (composerStartRestartGroup.changed(str) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i & 48) == 0) {
            i4 |= composerStartRestartGroup.changed(str2) ? 32 : 16;
        }
        int i17 = i3 & 4;
        if (i17 == 0) {
            if ((i & 384) == 0) {
                num2 = num;
                i4 |= composerStartRestartGroup.changed(num2) ? 256 : 128;
            }
            if ((i & 3072) == 0) {
                function4 = function0;
                if (composerStartRestartGroup.changedInstance(function4)) {
                    i16 = 2048;
                } else {
                    i16 = 1024;
                }
                i4 |= i16;
            } else {
                function4 = function0;
            }
            i5 = i3 & 16;
            if (i5 != 0) {
                if ((i & 24576) == 0) {
                    function5 = function1;
                    if (composerStartRestartGroup.changedInstance(function5)) {
                        i6 = GL20.GL_COLOR_BUFFER_BIT;
                    } else {
                        i6 = 8192;
                    }
                    i4 |= i6;
                }
                if ((i & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) != 0) {
                    if ((i3 & 32) == 0 || !composerStartRestartGroup.changedInstance(function2)) {
                        i15 = 65536;
                    } else {
                        i15 = ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ;
                    }
                    i4 |= i15;
                }
                if ((i & 1572864) == 0) {
                    if (composerStartRestartGroup.changed(z)) {
                        i14 = ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
                    } else {
                        i14 = 524288;
                    }
                    i4 |= i14;
                }
                if ((i & 12582912) == 0) {
                    if (composerStartRestartGroup.changed(z2)) {
                        i13 = 8388608;
                    } else {
                        i13 = 4194304;
                    }
                    i4 |= i13;
                }
                if ((i & 100663296) == 0) {
                    if (composerStartRestartGroup.changed(focusRequester)) {
                        i12 = 67108864;
                    } else {
                        i12 = 33554432;
                    }
                    i4 |= i12;
                }
                i7 = i3 & 512;
                if (i7 != 0) {
                    i4 |= 805306368;
                } else if ((i & 805306368) == 0) {
                    if (composerStartRestartGroup.changedInstance(function3)) {
                        i8 = 536870912;
                    } else {
                        i8 = 268435456;
                    }
                    i4 |= i8;
                }
                i9 = i2 | 6;
                if ((i4 & 306783379) == 306783378 || (i9 & 3) != 2) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (composerStartRestartGroup.shouldExecute(z3, i4 & 1)) {
                    composerStartRestartGroup.startDefaults();
                    if ((i & 1) != 0 || composerStartRestartGroup.getDefaultsInvalid()) {
                        if (i17 != 0) {
                            num2 = null;
                        }
                        if (i5 != 0) {
                            function5 = null;
                        }
                        if ((i3 & 32) != 0) {
                            i4 &= -458753;
                            function9 = function5;
                        } else {
                            function9 = function2;
                        }
                        if (i7 != 0) {
                            objRememberedValue = composerStartRestartGroup.rememberedValue();
                            if (objRememberedValue == Composer.Companion.getEmpty()) {
                                objRememberedValue = new t82(14);
                                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                            }
                            function10 = (Function1) objRememberedValue;
                        } else {
                            function10 = function3;
                        }
                        modifier3 = Modifier.Companion;
                        function11 = function10;
                        function12 = function5;
                        i10 = i4;
                    } else {
                        composerStartRestartGroup.skipToGroupEnd();
                        if ((i3 & 32) != 0) {
                            i4 &= -458753;
                        }
                        function9 = function2;
                        function11 = function3;
                        modifier3 = modifier;
                        i10 = i4;
                        function12 = function5;
                    }
                    composerStartRestartGroup.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(374056362, i10, i9, "com.dh.myembyapp.ui.screens.home.ServerButton (TopNavigation.kt:453)");
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
                    }
                    g81Var = (g81) composerStartRestartGroup.consume(k92.b);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1159792958, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-accentFollowsTheme> (Theme.kt:147)");
                    }
                    zBooleanValue = ((Boolean) composerStartRestartGroup.consume(k92.g)).booleanValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    boolean z6 = !zBooleanValue;
                    objRememberedValue2 = composerStartRestartGroup.rememberedValue();
                    companion = Composer.Companion;
                    if (objRememberedValue2 == companion.getEmpty()) {
                        objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerStartRestartGroup);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
                    }
                    coroutineScope = (CoroutineScope) objRememberedValue2;
                    objRememberedValue3 = composerStartRestartGroup.rememberedValue();
                    if (objRememberedValue3 == companion.getEmpty()) {
                        snapshotMutationPolicy = null;
                        objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
                    } else {
                        snapshotMutationPolicy = null;
                    }
                    mutableState = (MutableState) objRememberedValue3;
                    objRememberedValue4 = composerStartRestartGroup.rememberedValue();
                    if (objRememberedValue4 == companion.getEmpty()) {
                        objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy, 2, snapshotMutationPolicy);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue4);
                    }
                    mutableState2 = (MutableState) objRememberedValue4;
                    objRememberedValue5 = composerStartRestartGroup.rememberedValue();
                    if (objRememberedValue5 == companion.getEmpty()) {
                        snapshotMutationPolicy2 = null;
                        objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue5);
                    } else {
                        snapshotMutationPolicy2 = null;
                    }
                    mutableState3 = (MutableState) objRememberedValue5;
                    objRememberedValue6 = composerStartRestartGroup.rememberedValue();
                    if (objRememberedValue6 == companion.getEmpty()) {
                        objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy2, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
                    }
                    mutableState4 = (MutableState) objRememberedValue6;
                    if (z2) {
                        j = Color.Companion.getTransparent-0d7_KjU();
                    } else {
                        j = g81Var.G;
                    }
                    j2 = j;
                    Unit unit = Unit.INSTANCE;
                    objRememberedValue7 = composerStartRestartGroup.rememberedValue();
                    Integer num4 = num2;
                    if (objRememberedValue7 == companion.getEmpty()) {
                        objRememberedValue7 = new z1(mutableState2, mutableState3, mutableState4, 7);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue7);
                    }
                    EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue7, composerStartRestartGroup, 54);
                    Modifier modifier4 = SizeKt.widthIn-VpY3zN4(SizeKt.height-3ABfNKs(modifier3, Dp.constructor-impl(42.0f)), a, b);
                    if (focusRequester != null) {
                        modifierFocusRequester = FocusRequesterModifierKt.focusRequester(Modifier.Companion, focusRequester);
                    } else {
                        modifierFocusRequester = Modifier.Companion;
                    }
                    Modifier modifierThen = modifier4.then(modifierFocusRequester);
                    if ((i10 & 1879048192) == 536870912) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    objRememberedValue8 = composerStartRestartGroup.rememberedValue();
                    if (!z4 || objRememberedValue8 == companion.getEmpty()) {
                        objRememberedValue8 = new k1(function11, mutableState, mutableState2, mutableState3, mutableState4, 12);
                        mutableState5 = mutableState2;
                        mutableState6 = mutableState3;
                        mutableState7 = mutableState4;
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
                    } else {
                        mutableState7 = mutableState4;
                        mutableState5 = mutableState2;
                        mutableState6 = mutableState3;
                    }
                    Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifierThen, (Function1) objRememberedValue8);
                    if ((i10 & 57344) == 16384) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    zChangedInstance = z5 | composerStartRestartGroup.changedInstance(coroutineScope) | ((((i10 & 458752) ^ ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) <= 131072 && composerStartRestartGroup.changed(function9)) || (i10 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 131072);
                    objRememberedValue9 = composerStartRestartGroup.rememberedValue();
                    if (!zChangedInstance || objRememberedValue9 == companion.getEmpty()) {
                        Function0 function14 = function9;
                        objRememberedValue9 = new xa2(function12, coroutineScope, mutableState5, mutableState6, mutableState7, function14);
                        function13 = function14;
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
                    } else {
                        function13 = function9;
                    }
                    Modifier modifierOnPreviewKeyEvent = KeyInputModifierKt.onPreviewKeyEvent(modifierOnFocusChanged, (Function1) objRememberedValue9);
                    buttonDefaults = ButtonDefaults.INSTANCE;
                    if (z) {
                        if (zBooleanValue) {
                            j4 = g81Var.K;
                        } else {
                            j4 = Color.Companion.getWhite-0d7_KjU();
                        }
                        j3 = j4;
                    } else {
                        j3 = j2;
                    }
                    materialTheme = MaterialTheme.INSTANCE;
                    i11 = MaterialTheme.$stable;
                    long j5 = materialTheme.getColorScheme(composerStartRestartGroup, i11).getOnSurface-0d7_KjU();
                    if (zBooleanValue) {
                        composerStartRestartGroup.startReplaceGroup(-1922379309);
                        jColor = materialTheme.getColorScheme(composerStartRestartGroup, i11).getOnSecondary-0d7_KjU();
                        composerStartRestartGroup.endReplaceGroup();
                    } else {
                        composerStartRestartGroup.startReplaceGroup(-1922434489);
                        composerStartRestartGroup.endReplaceGroup();
                        jColor = ColorKt.Color(4279308561L);
                    }
                    long j6 = jColor;
                    int i18 = ButtonDefaults.$stable;
                    ButtonColors buttonColorsM345colorsoq7We08 = buttonDefaults.m345colorsoq7We08(j2, j5, j3, j6, 0L, 0L, 0L, 0L, composerStartRestartGroup, i18 << 24, PsExtractor.VIDEO_STREAM_MASK);
                    composer2 = composerStartRestartGroup;
                    if (z) {
                        f = 1.05f;
                    } else {
                        f = 1.0f;
                    }
                    ButtonScale buttonScaleScale$default = ButtonDefaults.scale$default(buttonDefaults, 0.0f, f, 0.0f, 0.0f, 0.0f, 29, null);
                    if (z) {
                        buttonGlowGlow$default = im0.a(g81Var);
                    } else {
                        buttonGlowGlow$default = ButtonDefaults.glow$default(buttonDefaults, null, null, null, 7, null);
                    }
                    ButtonGlow buttonGlow = buttonGlowGlow$default;
                    if (z) {
                        border = new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null);
                    } else {
                        border = new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(0.0f), Color.Companion.getTransparent-0d7_KjU()), 0.0f, null, 6, null);
                    }
                    ButtonKt.m348ButtonTCVpFMg(function4, modifierOnPreviewKeyEvent, null, false, buttonScaleScale$default, buttonGlow, null, buttonColorsM345colorsoq7We08, 0.0f, buttonDefaults.border(null, border, null, null, null, composer2, i18 << 15, 29), PaddingKt.PaddingValues-YgX7TsA(Dp.constructor-impl(10.0f), Dp.constructor-impl(3.0f)), null, ComposableLambdaKt.rememberComposableLambda(-1144869446, true, new x52(num4, str, str2, z, z6, mutableState), composer2, 54), composer2, (i10 >> 9) & 14, 390, 2380);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    num3 = num4;
                    function6 = function13;
                    modifier2 = modifier3;
                    function7 = function12;
                    function8 = function11;
                } else {
                    composer2 = composerStartRestartGroup;
                    composer2.skipToGroupEnd();
                    function6 = function2;
                    modifier2 = modifier;
                    num3 = num2;
                    function7 = function5;
                    function8 = function3;
                }
                scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
                if (scopeUpdateScopeEndRestartGroup != null) {
                    scopeUpdateScopeEndRestartGroup.updateScope(new tx1(str, str2, num3, function0, function7, function6, z, z2, focusRequester, function8, modifier2, i, i2, i3));
                }
            }
            i4 |= 24576;
            function5 = function1;
            if ((i & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) != 0) {
                if ((i3 & 32) == 0) {
                    i15 = 65536;
                } else {
                    i15 = 65536;
                }
                i4 |= i15;
            }
            if ((i & 1572864) == 0) {
                if (composerStartRestartGroup.changed(z)) {
                    i14 = ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
                } else {
                    i14 = 524288;
                }
                i4 |= i14;
            }
            if ((i & 12582912) == 0) {
                if (composerStartRestartGroup.changed(z2)) {
                    i13 = 8388608;
                } else {
                    i13 = 4194304;
                }
                i4 |= i13;
            }
            if ((i & 100663296) == 0) {
                if (composerStartRestartGroup.changed(focusRequester)) {
                    i12 = 67108864;
                } else {
                    i12 = 33554432;
                }
                i4 |= i12;
            }
            i7 = i3 & 512;
            if (i7 != 0) {
                i4 |= 805306368;
            } else if ((i & 805306368) == 0) {
                if (composerStartRestartGroup.changedInstance(function3)) {
                    i8 = 536870912;
                } else {
                    i8 = 268435456;
                }
                i4 |= i8;
            }
            i9 = i2 | 6;
            if ((i4 & 306783379) == 306783378) {
                z3 = true;
            } else {
                z3 = true;
            }
            if (composerStartRestartGroup.shouldExecute(z3, i4 & 1)) {
                composerStartRestartGroup.startDefaults();
                if ((i & 1) != 0) {
                    if (i17 != 0) {
                        num2 = null;
                    }
                    if (i5 != 0) {
                        function5 = null;
                    }
                    if ((i3 & 32) != 0) {
                        i4 &= -458753;
                        function9 = function5;
                    } else {
                        function9 = function2;
                    }
                    if (i7 != 0) {
                        objRememberedValue = composerStartRestartGroup.rememberedValue();
                        if (objRememberedValue == Composer.Companion.getEmpty()) {
                            objRememberedValue = new t82(14);
                            composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                        }
                        function10 = (Function1) objRememberedValue;
                    } else {
                        function10 = function3;
                    }
                    modifier3 = Modifier.Companion;
                    function11 = function10;
                    function12 = function5;
                    i10 = i4;
                } else {
                    if (i17 != 0) {
                        num2 = null;
                    }
                    if (i5 != 0) {
                        function5 = null;
                    }
                    if ((i3 & 32) != 0) {
                        i4 &= -458753;
                        function9 = function5;
                    } else {
                        function9 = function2;
                    }
                    if (i7 != 0) {
                        objRememberedValue = composerStartRestartGroup.rememberedValue();
                        if (objRememberedValue == Composer.Companion.getEmpty()) {
                            objRememberedValue = new t82(14);
                            composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                        }
                        function10 = (Function1) objRememberedValue;
                    } else {
                        function10 = function3;
                    }
                    modifier3 = Modifier.Companion;
                    function11 = function10;
                    function12 = function5;
                    i10 = i4;
                }
                composerStartRestartGroup.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(374056362, i10, i9, "com.dh.myembyapp.ui.screens.home.ServerButton (TopNavigation.kt:453)");
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
                }
                g81Var = (g81) composerStartRestartGroup.consume(k92.b);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1159792958, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-accentFollowsTheme> (Theme.kt:147)");
                }
                zBooleanValue = ((Boolean) composerStartRestartGroup.consume(k92.g)).booleanValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                boolean z7 = !zBooleanValue;
                objRememberedValue2 = composerStartRestartGroup.rememberedValue();
                companion = Composer.Companion;
                if (objRememberedValue2 == companion.getEmpty()) {
                    objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerStartRestartGroup);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
                }
                coroutineScope = (CoroutineScope) objRememberedValue2;
                objRememberedValue3 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue3 == companion.getEmpty()) {
                    snapshotMutationPolicy = null;
                    objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
                } else {
                    snapshotMutationPolicy = null;
                }
                mutableState = (MutableState) objRememberedValue3;
                objRememberedValue4 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue4 == companion.getEmpty()) {
                    objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy, 2, snapshotMutationPolicy);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue4);
                }
                mutableState2 = (MutableState) objRememberedValue4;
                objRememberedValue5 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue5 == companion.getEmpty()) {
                    snapshotMutationPolicy2 = null;
                    objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue5);
                } else {
                    snapshotMutationPolicy2 = null;
                }
                mutableState3 = (MutableState) objRememberedValue5;
                objRememberedValue6 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue6 == companion.getEmpty()) {
                    objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy2, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
                }
                mutableState4 = (MutableState) objRememberedValue6;
                if (z2) {
                    j = Color.Companion.getTransparent-0d7_KjU();
                } else {
                    j = g81Var.G;
                }
                j2 = j;
                Unit unit2 = Unit.INSTANCE;
                objRememberedValue7 = composerStartRestartGroup.rememberedValue();
                Integer num5 = num2;
                if (objRememberedValue7 == companion.getEmpty()) {
                    objRememberedValue7 = new z1(mutableState2, mutableState3, mutableState4, 7);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue7);
                }
                EffectsKt.DisposableEffect(unit2, (Function1) objRememberedValue7, composerStartRestartGroup, 54);
                Modifier modifier5 = SizeKt.widthIn-VpY3zN4(SizeKt.height-3ABfNKs(modifier3, Dp.constructor-impl(42.0f)), a, b);
                if (focusRequester != null) {
                    modifierFocusRequester = FocusRequesterModifierKt.focusRequester(Modifier.Companion, focusRequester);
                } else {
                    modifierFocusRequester = Modifier.Companion;
                }
                Modifier modifierThen2 = modifier5.then(modifierFocusRequester);
                if ((i10 & 1879048192) == 536870912) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                objRememberedValue8 = composerStartRestartGroup.rememberedValue();
                if (z4) {
                    objRememberedValue8 = new k1(function11, mutableState, mutableState2, mutableState3, mutableState4, 12);
                    mutableState5 = mutableState2;
                    mutableState6 = mutableState3;
                    mutableState7 = mutableState4;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
                } else {
                    objRememberedValue8 = new k1(function11, mutableState, mutableState2, mutableState3, mutableState4, 12);
                    mutableState5 = mutableState2;
                    mutableState6 = mutableState3;
                    mutableState7 = mutableState4;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
                }
                Modifier modifierOnFocusChanged2 = FocusChangedModifierKt.onFocusChanged(modifierThen2, (Function1) objRememberedValue8);
                if ((i10 & 57344) == 16384) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                zChangedInstance = z5 | composerStartRestartGroup.changedInstance(coroutineScope) | ((((i10 & 458752) ^ ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) <= 131072 && composerStartRestartGroup.changed(function9)) || (i10 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 131072);
                objRememberedValue9 = composerStartRestartGroup.rememberedValue();
                if (zChangedInstance) {
                    Function0 function15 = function9;
                    objRememberedValue9 = new xa2(function12, coroutineScope, mutableState5, mutableState6, mutableState7, function15);
                    function13 = function15;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
                } else {
                    Function0 function16 = function9;
                    objRememberedValue9 = new xa2(function12, coroutineScope, mutableState5, mutableState6, mutableState7, function16);
                    function13 = function16;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
                }
                Modifier modifierOnPreviewKeyEvent2 = KeyInputModifierKt.onPreviewKeyEvent(modifierOnFocusChanged2, (Function1) objRememberedValue9);
                buttonDefaults = ButtonDefaults.INSTANCE;
                if (z) {
                    if (zBooleanValue) {
                        j4 = Color.Companion.getWhite-0d7_KjU();
                    } else {
                        j4 = g81Var.K;
                    }
                    j3 = j4;
                } else {
                    j3 = j2;
                }
                materialTheme = MaterialTheme.INSTANCE;
                i11 = MaterialTheme.$stable;
                long j7 = materialTheme.getColorScheme(composerStartRestartGroup, i11).getOnSurface-0d7_KjU();
                if (zBooleanValue) {
                    composerStartRestartGroup.startReplaceGroup(-1922434489);
                    composerStartRestartGroup.endReplaceGroup();
                    jColor = ColorKt.Color(4279308561L);
                } else {
                    composerStartRestartGroup.startReplaceGroup(-1922379309);
                    jColor = materialTheme.getColorScheme(composerStartRestartGroup, i11).getOnSecondary-0d7_KjU();
                    composerStartRestartGroup.endReplaceGroup();
                }
                long j8 = jColor;
                int i19 = ButtonDefaults.$stable;
                ButtonColors buttonColorsM345colorsoq7We09 = buttonDefaults.m345colorsoq7We08(j2, j7, j3, j8, 0L, 0L, 0L, 0L, composerStartRestartGroup, i19 << 24, PsExtractor.VIDEO_STREAM_MASK);
                composer2 = composerStartRestartGroup;
                if (z) {
                    f = 1.05f;
                } else {
                    f = 1.0f;
                }
                ButtonScale buttonScaleScale$default2 = ButtonDefaults.scale$default(buttonDefaults, 0.0f, f, 0.0f, 0.0f, 0.0f, 29, null);
                if (z) {
                    buttonGlowGlow$default = im0.a(g81Var);
                } else {
                    buttonGlowGlow$default = ButtonDefaults.glow$default(buttonDefaults, null, null, null, 7, null);
                }
                ButtonGlow buttonGlow2 = buttonGlowGlow$default;
                if (z) {
                    border = new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null);
                } else {
                    border = new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(0.0f), Color.Companion.getTransparent-0d7_KjU()), 0.0f, null, 6, null);
                }
                ButtonKt.m348ButtonTCVpFMg(function4, modifierOnPreviewKeyEvent2, null, false, buttonScaleScale$default2, buttonGlow2, null, buttonColorsM345colorsoq7We09, 0.0f, buttonDefaults.border(null, border, null, null, null, composer2, i19 << 15, 29), PaddingKt.PaddingValues-YgX7TsA(Dp.constructor-impl(10.0f), Dp.constructor-impl(3.0f)), null, ComposableLambdaKt.rememberComposableLambda(-1144869446, true, new x52(num5, str, str2, z, z7, mutableState), composer2, 54), composer2, (i10 >> 9) & 14, 390, 2380);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                num3 = num5;
                function6 = function13;
                modifier2 = modifier3;
                function7 = function12;
                function8 = function11;
            } else {
                composer2 = composerStartRestartGroup;
                composer2.skipToGroupEnd();
                function6 = function2;
                modifier2 = modifier;
                num3 = num2;
                function7 = function5;
                function8 = function3;
            }
            scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
            if (scopeUpdateScopeEndRestartGroup != null) {
                scopeUpdateScopeEndRestartGroup.updateScope(new tx1(str, str2, num3, function0, function7, function6, z, z2, focusRequester, function8, modifier2, i, i2, i3));
            }
        }
        i4 |= 384;
        num2 = num;
        if ((i & 3072) == 0) {
            function4 = function0;
            if (composerStartRestartGroup.changedInstance(function4)) {
                i16 = 2048;
            } else {
                i16 = 1024;
            }
            i4 |= i16;
        } else {
            function4 = function0;
        }
        i5 = i3 & 16;
        if (i5 != 0) {
            if ((i & 24576) == 0) {
                function5 = function1;
                if (composerStartRestartGroup.changedInstance(function5)) {
                    i6 = GL20.GL_COLOR_BUFFER_BIT;
                } else {
                    i6 = 8192;
                }
                i4 |= i6;
            }
            if ((i & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) != 0) {
                if ((i3 & 32) == 0) {
                    i15 = 65536;
                } else {
                    i15 = 65536;
                }
                i4 |= i15;
            }
            if ((i & 1572864) == 0) {
                if (composerStartRestartGroup.changed(z)) {
                    i14 = ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
                } else {
                    i14 = 524288;
                }
                i4 |= i14;
            }
            if ((i & 12582912) == 0) {
                if (composerStartRestartGroup.changed(z2)) {
                    i13 = 8388608;
                } else {
                    i13 = 4194304;
                }
                i4 |= i13;
            }
            if ((i & 100663296) == 0) {
                if (composerStartRestartGroup.changed(focusRequester)) {
                    i12 = 67108864;
                } else {
                    i12 = 33554432;
                }
                i4 |= i12;
            }
            i7 = i3 & 512;
            if (i7 != 0) {
                i4 |= 805306368;
            } else if ((i & 805306368) == 0) {
                if (composerStartRestartGroup.changedInstance(function3)) {
                    i8 = 536870912;
                } else {
                    i8 = 268435456;
                }
                i4 |= i8;
            }
            i9 = i2 | 6;
            if ((i4 & 306783379) == 306783378) {
                z3 = true;
            } else {
                z3 = true;
            }
            if (composerStartRestartGroup.shouldExecute(z3, i4 & 1)) {
                composerStartRestartGroup.startDefaults();
                if ((i & 1) != 0) {
                    if (i17 != 0) {
                        num2 = null;
                    }
                    if (i5 != 0) {
                        function5 = null;
                    }
                    if ((i3 & 32) != 0) {
                        i4 &= -458753;
                        function9 = function5;
                    } else {
                        function9 = function2;
                    }
                    if (i7 != 0) {
                        objRememberedValue = composerStartRestartGroup.rememberedValue();
                        if (objRememberedValue == Composer.Companion.getEmpty()) {
                            objRememberedValue = new t82(14);
                            composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                        }
                        function10 = (Function1) objRememberedValue;
                    } else {
                        function10 = function3;
                    }
                    modifier3 = Modifier.Companion;
                    function11 = function10;
                    function12 = function5;
                    i10 = i4;
                } else {
                    if (i17 != 0) {
                        num2 = null;
                    }
                    if (i5 != 0) {
                        function5 = null;
                    }
                    if ((i3 & 32) != 0) {
                        i4 &= -458753;
                        function9 = function5;
                    } else {
                        function9 = function2;
                    }
                    if (i7 != 0) {
                        objRememberedValue = composerStartRestartGroup.rememberedValue();
                        if (objRememberedValue == Composer.Companion.getEmpty()) {
                            objRememberedValue = new t82(14);
                            composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                        }
                        function10 = (Function1) objRememberedValue;
                    } else {
                        function10 = function3;
                    }
                    modifier3 = Modifier.Companion;
                    function11 = function10;
                    function12 = function5;
                    i10 = i4;
                }
                composerStartRestartGroup.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(374056362, i10, i9, "com.dh.myembyapp.ui.screens.home.ServerButton (TopNavigation.kt:453)");
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
                }
                g81Var = (g81) composerStartRestartGroup.consume(k92.b);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1159792958, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-accentFollowsTheme> (Theme.kt:147)");
                }
                zBooleanValue = ((Boolean) composerStartRestartGroup.consume(k92.g)).booleanValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                boolean z8 = !zBooleanValue;
                objRememberedValue2 = composerStartRestartGroup.rememberedValue();
                companion = Composer.Companion;
                if (objRememberedValue2 == companion.getEmpty()) {
                    objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerStartRestartGroup);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
                }
                coroutineScope = (CoroutineScope) objRememberedValue2;
                objRememberedValue3 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue3 == companion.getEmpty()) {
                    snapshotMutationPolicy = null;
                    objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
                } else {
                    snapshotMutationPolicy = null;
                }
                mutableState = (MutableState) objRememberedValue3;
                objRememberedValue4 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue4 == companion.getEmpty()) {
                    objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy, 2, snapshotMutationPolicy);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue4);
                }
                mutableState2 = (MutableState) objRememberedValue4;
                objRememberedValue5 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue5 == companion.getEmpty()) {
                    snapshotMutationPolicy2 = null;
                    objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue5);
                } else {
                    snapshotMutationPolicy2 = null;
                }
                mutableState3 = (MutableState) objRememberedValue5;
                objRememberedValue6 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue6 == companion.getEmpty()) {
                    objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy2, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
                }
                mutableState4 = (MutableState) objRememberedValue6;
                if (z2) {
                    j = Color.Companion.getTransparent-0d7_KjU();
                } else {
                    j = g81Var.G;
                }
                j2 = j;
                Unit unit3 = Unit.INSTANCE;
                objRememberedValue7 = composerStartRestartGroup.rememberedValue();
                Integer num6 = num2;
                if (objRememberedValue7 == companion.getEmpty()) {
                    objRememberedValue7 = new z1(mutableState2, mutableState3, mutableState4, 7);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue7);
                }
                EffectsKt.DisposableEffect(unit3, (Function1) objRememberedValue7, composerStartRestartGroup, 54);
                Modifier modifier6 = SizeKt.widthIn-VpY3zN4(SizeKt.height-3ABfNKs(modifier3, Dp.constructor-impl(42.0f)), a, b);
                if (focusRequester != null) {
                    modifierFocusRequester = FocusRequesterModifierKt.focusRequester(Modifier.Companion, focusRequester);
                } else {
                    modifierFocusRequester = Modifier.Companion;
                }
                Modifier modifierThen3 = modifier6.then(modifierFocusRequester);
                if ((i10 & 1879048192) == 536870912) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                objRememberedValue8 = composerStartRestartGroup.rememberedValue();
                if (z4) {
                    objRememberedValue8 = new k1(function11, mutableState, mutableState2, mutableState3, mutableState4, 12);
                    mutableState5 = mutableState2;
                    mutableState6 = mutableState3;
                    mutableState7 = mutableState4;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
                } else {
                    objRememberedValue8 = new k1(function11, mutableState, mutableState2, mutableState3, mutableState4, 12);
                    mutableState5 = mutableState2;
                    mutableState6 = mutableState3;
                    mutableState7 = mutableState4;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
                }
                Modifier modifierOnFocusChanged3 = FocusChangedModifierKt.onFocusChanged(modifierThen3, (Function1) objRememberedValue8);
                if ((i10 & 57344) == 16384) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                zChangedInstance = z5 | composerStartRestartGroup.changedInstance(coroutineScope) | ((((i10 & 458752) ^ ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) <= 131072 && composerStartRestartGroup.changed(function9)) || (i10 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 131072);
                objRememberedValue9 = composerStartRestartGroup.rememberedValue();
                if (zChangedInstance) {
                    Function0 function17 = function9;
                    objRememberedValue9 = new xa2(function12, coroutineScope, mutableState5, mutableState6, mutableState7, function17);
                    function13 = function17;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
                } else {
                    Function0 function18 = function9;
                    objRememberedValue9 = new xa2(function12, coroutineScope, mutableState5, mutableState6, mutableState7, function18);
                    function13 = function18;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
                }
                Modifier modifierOnPreviewKeyEvent3 = KeyInputModifierKt.onPreviewKeyEvent(modifierOnFocusChanged3, (Function1) objRememberedValue9);
                buttonDefaults = ButtonDefaults.INSTANCE;
                if (z) {
                    if (zBooleanValue) {
                        j4 = Color.Companion.getWhite-0d7_KjU();
                    } else {
                        j4 = g81Var.K;
                    }
                    j3 = j4;
                } else {
                    j3 = j2;
                }
                materialTheme = MaterialTheme.INSTANCE;
                i11 = MaterialTheme.$stable;
                long j9 = materialTheme.getColorScheme(composerStartRestartGroup, i11).getOnSurface-0d7_KjU();
                if (zBooleanValue) {
                    composerStartRestartGroup.startReplaceGroup(-1922434489);
                    composerStartRestartGroup.endReplaceGroup();
                    jColor = ColorKt.Color(4279308561L);
                } else {
                    composerStartRestartGroup.startReplaceGroup(-1922379309);
                    jColor = materialTheme.getColorScheme(composerStartRestartGroup, i11).getOnSecondary-0d7_KjU();
                    composerStartRestartGroup.endReplaceGroup();
                }
                long j10 = jColor;
                int i110 = ButtonDefaults.$stable;
                ButtonColors buttonColorsM345colorsoq7We010 = buttonDefaults.m345colorsoq7We08(j2, j9, j3, j10, 0L, 0L, 0L, 0L, composerStartRestartGroup, i110 << 24, PsExtractor.VIDEO_STREAM_MASK);
                composer2 = composerStartRestartGroup;
                if (z) {
                    f = 1.05f;
                } else {
                    f = 1.0f;
                }
                ButtonScale buttonScaleScale$default3 = ButtonDefaults.scale$default(buttonDefaults, 0.0f, f, 0.0f, 0.0f, 0.0f, 29, null);
                if (z) {
                    buttonGlowGlow$default = im0.a(g81Var);
                } else {
                    buttonGlowGlow$default = ButtonDefaults.glow$default(buttonDefaults, null, null, null, 7, null);
                }
                ButtonGlow buttonGlow3 = buttonGlowGlow$default;
                if (z) {
                    border = new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null);
                } else {
                    border = new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(0.0f), Color.Companion.getTransparent-0d7_KjU()), 0.0f, null, 6, null);
                }
                ButtonKt.m348ButtonTCVpFMg(function4, modifierOnPreviewKeyEvent3, null, false, buttonScaleScale$default3, buttonGlow3, null, buttonColorsM345colorsoq7We010, 0.0f, buttonDefaults.border(null, border, null, null, null, composer2, i110 << 15, 29), PaddingKt.PaddingValues-YgX7TsA(Dp.constructor-impl(10.0f), Dp.constructor-impl(3.0f)), null, ComposableLambdaKt.rememberComposableLambda(-1144869446, true, new x52(num6, str, str2, z, z8, mutableState), composer2, 54), composer2, (i10 >> 9) & 14, 390, 2380);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                num3 = num6;
                function6 = function13;
                modifier2 = modifier3;
                function7 = function12;
                function8 = function11;
            } else {
                composer2 = composerStartRestartGroup;
                composer2.skipToGroupEnd();
                function6 = function2;
                modifier2 = modifier;
                num3 = num2;
                function7 = function5;
                function8 = function3;
            }
            scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
            if (scopeUpdateScopeEndRestartGroup != null) {
                scopeUpdateScopeEndRestartGroup.updateScope(new tx1(str, str2, num3, function0, function7, function6, z, z2, focusRequester, function8, modifier2, i, i2, i3));
            }
        }
        i4 |= 24576;
        function5 = function1;
        if ((i & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) != 0) {
            if ((i3 & 32) == 0) {
                i15 = 65536;
            } else {
                i15 = 65536;
            }
            i4 |= i15;
        }
        if ((i & 1572864) == 0) {
            if (composerStartRestartGroup.changed(z)) {
                i14 = ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
            } else {
                i14 = 524288;
            }
            i4 |= i14;
        }
        if ((i & 12582912) == 0) {
            if (composerStartRestartGroup.changed(z2)) {
                i13 = 8388608;
            } else {
                i13 = 4194304;
            }
            i4 |= i13;
        }
        if ((i & 100663296) == 0) {
            if (composerStartRestartGroup.changed(focusRequester)) {
                i12 = 67108864;
            } else {
                i12 = 33554432;
            }
            i4 |= i12;
        }
        i7 = i3 & 512;
        if (i7 != 0) {
            i4 |= 805306368;
        } else if ((i & 805306368) == 0) {
            if (composerStartRestartGroup.changedInstance(function3)) {
                i8 = 536870912;
            } else {
                i8 = 268435456;
            }
            i4 |= i8;
        }
        i9 = i2 | 6;
        if ((i4 & 306783379) == 306783378) {
            z3 = true;
        } else {
            z3 = true;
        }
        if (composerStartRestartGroup.shouldExecute(z3, i4 & 1)) {
            composerStartRestartGroup.startDefaults();
            if ((i & 1) != 0) {
                if (i17 != 0) {
                    num2 = null;
                }
                if (i5 != 0) {
                    function5 = null;
                }
                if ((i3 & 32) != 0) {
                    i4 &= -458753;
                    function9 = function5;
                } else {
                    function9 = function2;
                }
                if (i7 != 0) {
                    objRememberedValue = composerStartRestartGroup.rememberedValue();
                    if (objRememberedValue == Composer.Companion.getEmpty()) {
                        objRememberedValue = new t82(14);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                    }
                    function10 = (Function1) objRememberedValue;
                } else {
                    function10 = function3;
                }
                modifier3 = Modifier.Companion;
                function11 = function10;
                function12 = function5;
                i10 = i4;
            } else {
                if (i17 != 0) {
                    num2 = null;
                }
                if (i5 != 0) {
                    function5 = null;
                }
                if ((i3 & 32) != 0) {
                    i4 &= -458753;
                    function9 = function5;
                } else {
                    function9 = function2;
                }
                if (i7 != 0) {
                    objRememberedValue = composerStartRestartGroup.rememberedValue();
                    if (objRememberedValue == Composer.Companion.getEmpty()) {
                        objRememberedValue = new t82(14);
                        composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                    }
                    function10 = (Function1) objRememberedValue;
                } else {
                    function10 = function3;
                }
                modifier3 = Modifier.Companion;
                function11 = function10;
                function12 = function5;
                i10 = i4;
            }
            composerStartRestartGroup.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(374056362, i10, i9, "com.dh.myembyapp.ui.screens.home.ServerButton (TopNavigation.kt:453)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1159792958, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-accentFollowsTheme> (Theme.kt:147)");
            }
            zBooleanValue = ((Boolean) composerStartRestartGroup.consume(k92.g)).booleanValue();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            boolean z9 = !zBooleanValue;
            objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            companion = Composer.Companion;
            if (objRememberedValue2 == companion.getEmpty()) {
                objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerStartRestartGroup);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            coroutineScope = (CoroutineScope) objRememberedValue2;
            objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue3 == companion.getEmpty()) {
                snapshotMutationPolicy = null;
                objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
            } else {
                snapshotMutationPolicy = null;
            }
            mutableState = (MutableState) objRememberedValue3;
            objRememberedValue4 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue4 == companion.getEmpty()) {
                objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy, 2, snapshotMutationPolicy);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue4);
            }
            mutableState2 = (MutableState) objRememberedValue4;
            objRememberedValue5 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue5 == companion.getEmpty()) {
                snapshotMutationPolicy2 = null;
                objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue5);
            } else {
                snapshotMutationPolicy2 = null;
            }
            mutableState3 = (MutableState) objRememberedValue5;
            objRememberedValue6 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue6 == companion.getEmpty()) {
                objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy2, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
            }
            mutableState4 = (MutableState) objRememberedValue6;
            if (z2) {
                j = Color.Companion.getTransparent-0d7_KjU();
            } else {
                j = g81Var.G;
            }
            j2 = j;
            Unit unit4 = Unit.INSTANCE;
            objRememberedValue7 = composerStartRestartGroup.rememberedValue();
            Integer num7 = num2;
            if (objRememberedValue7 == companion.getEmpty()) {
                objRememberedValue7 = new z1(mutableState2, mutableState3, mutableState4, 7);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue7);
            }
            EffectsKt.DisposableEffect(unit4, (Function1) objRememberedValue7, composerStartRestartGroup, 54);
            Modifier modifier7 = SizeKt.widthIn-VpY3zN4(SizeKt.height-3ABfNKs(modifier3, Dp.constructor-impl(42.0f)), a, b);
            if (focusRequester != null) {
                modifierFocusRequester = FocusRequesterModifierKt.focusRequester(Modifier.Companion, focusRequester);
            } else {
                modifierFocusRequester = Modifier.Companion;
            }
            Modifier modifierThen4 = modifier7.then(modifierFocusRequester);
            if ((i10 & 1879048192) == 536870912) {
                z4 = true;
            } else {
                z4 = false;
            }
            objRememberedValue8 = composerStartRestartGroup.rememberedValue();
            if (z4) {
                objRememberedValue8 = new k1(function11, mutableState, mutableState2, mutableState3, mutableState4, 12);
                mutableState5 = mutableState2;
                mutableState6 = mutableState3;
                mutableState7 = mutableState4;
                composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
            } else {
                objRememberedValue8 = new k1(function11, mutableState, mutableState2, mutableState3, mutableState4, 12);
                mutableState5 = mutableState2;
                mutableState6 = mutableState3;
                mutableState7 = mutableState4;
                composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
            }
            Modifier modifierOnFocusChanged4 = FocusChangedModifierKt.onFocusChanged(modifierThen4, (Function1) objRememberedValue8);
            if ((i10 & 57344) == 16384) {
                z5 = true;
            } else {
                z5 = false;
            }
            zChangedInstance = z5 | composerStartRestartGroup.changedInstance(coroutineScope) | ((((i10 & 458752) ^ ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) <= 131072 && composerStartRestartGroup.changed(function9)) || (i10 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 131072);
            objRememberedValue9 = composerStartRestartGroup.rememberedValue();
            if (zChangedInstance) {
                Function0 function19 = function9;
                objRememberedValue9 = new xa2(function12, coroutineScope, mutableState5, mutableState6, mutableState7, function19);
                function13 = function19;
                composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
            } else {
                Function0 function110 = function9;
                objRememberedValue9 = new xa2(function12, coroutineScope, mutableState5, mutableState6, mutableState7, function110);
                function13 = function110;
                composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
            }
            Modifier modifierOnPreviewKeyEvent4 = KeyInputModifierKt.onPreviewKeyEvent(modifierOnFocusChanged4, (Function1) objRememberedValue9);
            buttonDefaults = ButtonDefaults.INSTANCE;
            if (z) {
                if (zBooleanValue) {
                    j4 = Color.Companion.getWhite-0d7_KjU();
                } else {
                    j4 = g81Var.K;
                }
                j3 = j4;
            } else {
                j3 = j2;
            }
            materialTheme = MaterialTheme.INSTANCE;
            i11 = MaterialTheme.$stable;
            long j11 = materialTheme.getColorScheme(composerStartRestartGroup, i11).getOnSurface-0d7_KjU();
            if (zBooleanValue) {
                composerStartRestartGroup.startReplaceGroup(-1922434489);
                composerStartRestartGroup.endReplaceGroup();
                jColor = ColorKt.Color(4279308561L);
            } else {
                composerStartRestartGroup.startReplaceGroup(-1922379309);
                jColor = materialTheme.getColorScheme(composerStartRestartGroup, i11).getOnSecondary-0d7_KjU();
                composerStartRestartGroup.endReplaceGroup();
            }
            long j12 = jColor;
            int i111 = ButtonDefaults.$stable;
            ButtonColors buttonColorsM345colorsoq7We011 = buttonDefaults.m345colorsoq7We08(j2, j11, j3, j12, 0L, 0L, 0L, 0L, composerStartRestartGroup, i111 << 24, PsExtractor.VIDEO_STREAM_MASK);
            composer2 = composerStartRestartGroup;
            if (z) {
                f = 1.05f;
            } else {
                f = 1.0f;
            }
            ButtonScale buttonScaleScale$default4 = ButtonDefaults.scale$default(buttonDefaults, 0.0f, f, 0.0f, 0.0f, 0.0f, 29, null);
            if (z) {
                buttonGlowGlow$default = im0.a(g81Var);
            } else {
                buttonGlowGlow$default = ButtonDefaults.glow$default(buttonDefaults, null, null, null, 7, null);
            }
            ButtonGlow buttonGlow4 = buttonGlowGlow$default;
            if (z) {
                border = new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null);
            } else {
                border = new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(0.0f), Color.Companion.getTransparent-0d7_KjU()), 0.0f, null, 6, null);
            }
            ButtonKt.m348ButtonTCVpFMg(function4, modifierOnPreviewKeyEvent4, null, false, buttonScaleScale$default4, buttonGlow4, null, buttonColorsM345colorsoq7We011, 0.0f, buttonDefaults.border(null, border, null, null, null, composer2, i111 << 15, 29), PaddingKt.PaddingValues-YgX7TsA(Dp.constructor-impl(10.0f), Dp.constructor-impl(3.0f)), null, ComposableLambdaKt.rememberComposableLambda(-1144869446, true, new x52(num7, str, str2, z, z9, mutableState), composer2, 54), composer2, (i10 >> 9) & 14, 390, 2380);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            num3 = num7;
            function6 = function13;
            modifier2 = modifier3;
            function7 = function12;
            function8 = function11;
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
            function6 = function2;
            modifier2 = modifier;
            num3 = num2;
            function7 = function5;
            function8 = function3;
        }
        scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new tx1(str, str2, num3, function0, function7, function6, z, z2, focusRequester, function8, modifier2, i, i2, i3));
        }
    }

    public static final void c(ImageVector imageVector, Function0 function0, boolean z, boolean z2, boolean z3, FocusRequester focusRequester, Modifier modifier, Composer composer, int i) {
        int i2;
        Composer composer2;
        Modifier modifier2;
        long j;
        long jU;
        imageVector.getClass();
        function0.getClass();
        Composer composerStartRestartGroup = composer.startRestartGroup(1939969201);
        if ((i & 6) == 0) {
            i2 = (composerStartRestartGroup.changed(imageVector) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changed("设置") ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerStartRestartGroup.changed(z) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerStartRestartGroup.changed(z2) ? GL20.GL_COLOR_BUFFER_BIT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerStartRestartGroup.changed(z3) ? ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerStartRestartGroup.changed(focusRequester) ? ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES : 524288;
        }
        int i3 = i2 | 12582912;
        if (composerStartRestartGroup.shouldExecute((4793491 & i3) != 4793490, i3 & 1)) {
            Modifier modifier3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1939969201, i3, -1, "com.dh.myembyapp.ui.screens.home.TopNavIconButton (TopNavigation.kt:634)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1159792958, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-accentFollowsTheme> (Theme.kt:147)");
            }
            boolean zBooleanValue = ((Boolean) composerStartRestartGroup.consume(k92.g)).booleanValue();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            boolean z4 = !zBooleanValue;
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            long j2 = z2 ? Color.Companion.getTransparent-0d7_KjU() : g81Var.G;
            Modifier modifier4 = SizeKt.size-3ABfNKs(modifier3, Dp.constructor-impl(40.0f));
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue2 == companion.getEmpty()) {
                objRememberedValue2 = new pz1(13, mutableState);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            Modifier modifierThen = FocusChangedModifierKt.onFocusChanged(modifier4, (Function1) objRememberedValue2).then(focusRequester != null ? FocusRequesterModifierKt.focusRequester(modifier3, focusRequester) : modifier3);
            ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
            ButtonShape buttonShapeShape$default = ButtonDefaults.shape$default(buttonDefaults, RoundedCornerShapeKt.getCircleShape(), null, null, null, null, 30, null);
            if (z) {
                j = !zBooleanValue ? Color.Companion.getWhite-0d7_KjU() : g81Var.H;
            } else {
                j = j2;
            }
            MaterialTheme materialTheme = MaterialTheme.INSTANCE;
            int i4 = MaterialTheme.$stable;
            long j3 = materialTheme.getColorScheme(composerStartRestartGroup, i4).getOnSurface-0d7_KjU();
            if (zBooleanValue) {
                jU = bc0.u(composerStartRestartGroup, 294648186, materialTheme, composerStartRestartGroup, i4);
            } else {
                composerStartRestartGroup.startReplaceGroup(294646626);
                composerStartRestartGroup.endReplaceGroup();
                jU = ColorKt.Color(4279308561L);
            }
            int i5 = ButtonDefaults.$stable;
            modifier2 = modifier3;
            ButtonKt.m348ButtonTCVpFMg(function0, modifierThen, null, false, ButtonDefaults.scale$default(buttonDefaults, 0.0f, z ? 1.05f : 1.0f, 0.0f, 0.0f, 0.0f, 29, null), z ? im0.a(g81Var) : ButtonDefaults.glow$default(buttonDefaults, null, null, null, 7, null), buttonShapeShape$default, buttonDefaults.m345colorsoq7We08(j2, j3, j, jU, 0L, 0L, 0L, 0L, composerStartRestartGroup, i5 << 24, PsExtractor.VIDEO_STREAM_MASK), 0.0f, buttonDefaults.border(null, z ? new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null) : new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(0.0f), Color.Companion.getTransparent-0d7_KjU()), 0.0f, null, 6, null), null, null, null, composerStartRestartGroup, i5 << 15, 29), PaddingKt.PaddingValues-0680j_4(Dp.constructor-impl(0.0f)), null, ComposableLambdaKt.rememberComposableLambda(-760534367, true, new m62(imageVector, z, z4, z3, mutableState), composerStartRestartGroup, 54), composerStartRestartGroup, (i3 >> 6) & 14, 390, 2316);
            composer2 = composerStartRestartGroup;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
            modifier2 = modifier;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new v8(imageVector, function0, z, z2, z3, focusRequester, modifier2, i));
        }
    }

    public static final void d(final to0 to0Var, final Function1 function1, final boolean z, final boolean z2, final String str, final String str2, final Function0 function0, final Function0 function2, final Function0 function3, final Function0 function4, final Function0 function5, final boolean z3, final boolean z4, final boolean z5, final boolean z6, final boolean z7, final FocusRequester focusRequester, final int i, final int i2, final int i3, final Function0 function6, final Function0 function7, final Function1 function8, final Modifier modifier, Composer composer, final int i4, final int i5, final int i6) {
        int i7;
        int i8;
        int i9;
        Composer composer2;
        Composer composer3;
        char c2;
        Boolean bool;
        FocusRequester focusRequester2;
        FocusRequester focusRequester3;
        MutableState mutableState;
        MutableState mutableState2;
        to0 to0Var2;
        FocusRequester focusRequester4;
        boolean z8;
        Object g7Var;
        FocusRequester focusRequester5;
        MutableState mutableState3;
        Integer num;
        int i10;
        to0Var.getClass();
        function1.getClass();
        Composer composerStartRestartGroup = composer.startRestartGroup(457920389);
        if ((i4 & 6) == 0) {
            i7 = (composerStartRestartGroup.changed(to0Var.ordinal()) ? 4 : 2) | i4;
        } else {
            i7 = i4;
        }
        int i11 = i7;
        if ((i4 & 48) == 0) {
            i11 |= composerStartRestartGroup.changedInstance(function1) ? 32 : 16;
        }
        if ((i4 & 384) == 0) {
            i11 |= composerStartRestartGroup.changed(z) ? 256 : 128;
        }
        if ((i4 & 3072) == 0) {
            i11 |= composerStartRestartGroup.changed(z2) ? 2048 : 1024;
        }
        if ((i4 & 24576) == 0) {
            i11 |= composerStartRestartGroup.changed(str) ? 16384 : 8192;
        }
        if ((i4 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            i11 |= composerStartRestartGroup.changed(str2) ? 131072 : 65536;
        }
        if ((i4 & 1572864) == 0) {
            i11 |= composerStartRestartGroup.changedInstance(function0) ? ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES : 524288;
        }
        if ((i4 & 12582912) == 0) {
            i11 |= composerStartRestartGroup.changedInstance(function2) ? 8388608 : 4194304;
        }
        if ((i4 & 100663296) == 0) {
            i11 |= composerStartRestartGroup.changedInstance(function3) ? 67108864 : 33554432;
        }
        if ((i4 & 805306368) == 0) {
            i11 |= composerStartRestartGroup.changedInstance(function4) ? 536870912 : 268435456;
        }
        int i12 = i11;
        if ((i5 & 6) == 0) {
            i8 = i5 | (composerStartRestartGroup.changedInstance(function5) ? 4 : 2);
        } else {
            i8 = i5;
        }
        if ((i5 & 48) == 0) {
            i8 |= composerStartRestartGroup.changed(z3) ? 32 : 16;
        }
        if ((i5 & 384) == 0) {
            i8 |= composerStartRestartGroup.changed(z4) ? 256 : 128;
        }
        if ((i5 & 3072) == 0) {
            i8 |= composerStartRestartGroup.changed(z5) ? 2048 : 1024;
        }
        if ((i5 & 24576) == 0) {
            i8 |= composerStartRestartGroup.changed(z6) ? 16384 : 8192;
        }
        if ((i5 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            i8 |= composerStartRestartGroup.changed(z7) ? 131072 : 65536;
        }
        if ((i5 & 1572864) == 0) {
            i8 |= composerStartRestartGroup.changed(focusRequester) ? ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES : 524288;
        }
        if ((i5 & 12582912) == 0) {
            i8 |= composerStartRestartGroup.changed(i) ? 8388608 : 4194304;
        }
        if ((i5 & 100663296) == 0) {
            i8 |= composerStartRestartGroup.changed(i2) ? 67108864 : 33554432;
        }
        if ((i5 & 805306368) == 0) {
            i8 |= composerStartRestartGroup.changed(i3) ? 536870912 : 268435456;
        }
        int i13 = i8;
        if ((i6 & 6) == 0) {
            i9 = i6 | (composerStartRestartGroup.changedInstance(function6) ? 4 : 2);
        } else {
            i9 = i6;
        }
        if ((i6 & 48) == 0) {
            i9 |= composerStartRestartGroup.changedInstance(function7) ? 32 : 16;
        }
        if ((i6 & 384) == 0) {
            i9 |= composerStartRestartGroup.changedInstance(function8) ? 256 : 128;
        }
        if ((i6 & 3072) == 0) {
            i9 |= composerStartRestartGroup.changed(modifier) ? 2048 : 1024;
        }
        int i14 = i9;
        if (composerStartRestartGroup.shouldExecute(((i12 & 306783379) == 306783378 && (i13 & 306783379) == 306783378 && (i14 & 1171) == 1170) ? false : true, i12 & 1)) {
            composerStartRestartGroup.startDefaults();
            if ((i4 & 1) != 0 && !composerStartRestartGroup.getDefaultsInvalid()) {
                composerStartRestartGroup.skipToGroupEnd();
            }
            composerStartRestartGroup.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(457920389, i12, i13, "com.dh.myembyapp.ui.screens.home.TopNavigation (TopNavigation.kt:91)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                objRememberedValue = zr.n(composerStartRestartGroup);
            }
            FocusRequester focusRequester6 = (FocusRequester) objRememberedValue;
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue2 == companion.getEmpty()) {
                objRememberedValue2 = zr.n(composerStartRestartGroup);
            }
            FocusRequester focusRequester7 = (FocusRequester) objRememberedValue2;
            Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue3 == companion.getEmpty()) {
                objRememberedValue3 = zr.n(composerStartRestartGroup);
            }
            FocusRequester focusRequester8 = (FocusRequester) objRememberedValue3;
            Object objRememberedValue4 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue4 == companion.getEmpty()) {
                objRememberedValue4 = zr.n(composerStartRestartGroup);
            }
            FocusRequester focusRequester9 = (FocusRequester) objRememberedValue4;
            Object objRememberedValue5 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue5 == companion.getEmpty()) {
                objRememberedValue5 = zr.n(composerStartRestartGroup);
            }
            FocusRequester focusRequester10 = (FocusRequester) objRememberedValue5;
            Object objRememberedValue6 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue6 == companion.getEmpty()) {
                MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default);
                objRememberedValue6 = mutableStateMutableStateOf$default;
            }
            MutableState mutableState4 = (MutableState) objRememberedValue6;
            Object objRememberedValue7 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue7 == companion.getEmpty()) {
                objRememberedValue7 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue7);
            }
            MutableState mutableState5 = (MutableState) objRememberedValue7;
            Object objRememberedValue8 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue8 == companion.getEmpty()) {
                objRememberedValue8 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
            }
            MutableState mutableState6 = (MutableState) objRememberedValue8;
            Boolean bool2 = (Boolean) mutableState4.getValue();
            bool2.getClass();
            boolean z9 = (i12 & 14) == 4;
            Object objRememberedValue9 = composerStartRestartGroup.rememberedValue();
            if (z9 || objRememberedValue9 == companion.getEmpty()) {
                composer3 = composerStartRestartGroup;
                c2 = 0;
                bool = bool2;
                w6 w6Var = new w6(to0Var, focusRequester7, focusRequester8, focusRequester9, mutableState4, mutableState5, mutableState6, (Continuation) null);
                focusRequester2 = focusRequester8;
                focusRequester3 = focusRequester9;
                mutableState = mutableState4;
                mutableState2 = mutableState5;
                to0Var2 = to0Var;
                focusRequester4 = focusRequester7;
                composer3.updateRememberedValue(w6Var);
                objRememberedValue9 = w6Var;
            } else {
                to0Var2 = to0Var;
                focusRequester3 = focusRequester9;
                composer3 = composerStartRestartGroup;
                focusRequester4 = focusRequester7;
                focusRequester2 = focusRequester8;
                mutableState2 = mutableState5;
                c2 = 0;
                bool = bool2;
                mutableState = mutableState4;
            }
            int i15 = r25 << 3;
            EffectsKt.LaunchedEffect(bool, to0Var2, (Function2) objRememberedValue9, composer3, i15 & 112);
            Integer numValueOf = Integer.valueOf(i2);
            boolean z10 = ((i13 & 234881024) == c2) | ((i14 & 112) == 32);
            Object objRememberedValue10 = composer3.rememberedValue();
            if (z10 || objRememberedValue10 == companion.getEmpty()) {
                z8 = false;
                objRememberedValue10 = new hc(i2, function7, focusRequester4, (Continuation) null);
                composer3.updateRememberedValue(objRememberedValue10);
            } else {
                z8 = false;
            }
            EffectsKt.LaunchedEffect(numValueOf, (Function2) objRememberedValue10, composer3, (i13 >> 24) & 14);
            Integer numValueOf2 = Integer.valueOf(i);
            boolean z11 = ((i13 & 29360128) == 8388608) | ((i14 & 14) == 4);
            Object objRememberedValue11 = composer3.rememberedValue();
            if (z11 || objRememberedValue11 == companion.getEmpty()) {
                focusRequester5 = focusRequester6;
                mutableState3 = mutableState6;
                num = numValueOf2;
                g7Var = new g7(i, function6, mutableState3, focusRequester5, (Continuation) null);
                composer3.updateRememberedValue(g7Var);
            } else {
                focusRequester5 = focusRequester6;
                num = numValueOf2;
                g7Var = objRememberedValue11;
                mutableState3 = mutableState6;
            }
            EffectsKt.LaunchedEffect(num, (Function2) g7Var, composer3, (i13 >> 21) & 14);
            Integer numValueOf3 = Integer.valueOf(i3);
            boolean z12 = (i13 & 1879048192) == 536870912;
            Object objRememberedValue12 = composer3.rememberedValue();
            if (z12 || objRememberedValue12 == companion.getEmpty()) {
                objRememberedValue12 = new dw0(i3, mutableState3, (FocusRequester) r23, (Continuation) z8);
                composer3.updateRememberedValue(objRememberedValue12);
            }
            EffectsKt.LaunchedEffect(numValueOf3, (Function2) objRememberedValue12, composer3, (i13 >> 27) & 14);
            Unit unit = Unit.INSTANCE;
            boolean z13 = (i14 & 896) == 256;
            Object objRememberedValue13 = composer3.rememberedValue();
            if (z13 || objRememberedValue13 == companion.getEmpty()) {
                i10 = 0;
                objRememberedValue13 = new ua2(function8, 0);
                composer3.updateRememberedValue(objRememberedValue13);
            } else {
                i10 = 0;
            }
            EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue13, composer3, 6);
            State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z3 ? 0.0f : -200.0f, AnimationSpecKt.tween$default(150, i10, z8, 6, z8), 0.0f, "navOffsetY", (Function1) null, composer3, 3120, 20);
            Modifier modifierZIndex = ZIndexModifierKt.zIndex(SizeKt.height-3ABfNKs(SizeKt.fillMaxWidth$default(modifier, 0.0f, 1, z8), Dp.constructor-impl(72.0f)), 10.0f);
            boolean zChanged = composer3.changed(stateAnimateFloatAsState);
            Object objRememberedValue14 = composer3.rememberedValue();
            if (zChanged || objRememberedValue14 == companion.getEmpty()) {
                objRememberedValue14 = new u4(stateAnimateFloatAsState, 8);
                composer3.updateRememberedValue(objRememberedValue14);
            }
            Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierZIndex, (Function1) objRememberedValue14);
            boolean z14 = ((i13 & 112) == 32) | ((i13 & 3670016) == 1048576);
            Object objRememberedValue15 = composer3.rememberedValue();
            if (z14 || objRememberedValue15 == companion.getEmpty()) {
                objRememberedValue15 = new x4(z3, focusRequester, 1);
                composer3.updateRememberedValue(objRememberedValue15);
            }
            Modifier modifierFocusProperties = FocusPropertiesKt.focusProperties(modifierGraphicsLayer, (Function1) objRememberedValue15);
            Alignment.Companion companion2 = Alignment.Companion;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(companion2.getTopStart(), false);
            long currentCompositeKeyHashCode = ComposablesKt.getCurrentCompositeKeyHashCode(composer3, 0);
            int i16 = (int) (currentCompositeKeyHashCode ^ (currentCompositeKeyHashCode >>> 32));
            CompositionLocalMap currentCompositionLocalMap = composer3.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierFocusProperties);
            ComposeUiNode.Companion companion3 = ComposeUiNode.Companion;
            Function0 constructor = companion3.getConstructor();
            if (composer3.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composer3.startReusableNode();
            if (composer3.getInserting()) {
                composer3.createNode(constructor);
            } else {
                composer3.useNode();
            }
            Composer composer4 = Updater.constructor-impl(composer3);
            zr.z(companion3, composer4, measurePolicyMaybeCachedBoxMeasurePolicy, composer4, currentCompositionLocalMap);
            Updater.set-impl(composer4, modifierMaterializeModifier, pd.h(companion3, composer4, Integer.valueOf(i16), composer4));
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            Modifier.Companion companion4 = Modifier.Companion;
            Modifier modifier2 = PaddingKt.padding-VpY3zN4(SizeKt.fillMaxWidth$default(companion4, 0.0f, 1, (Object) null), Dp.constructor-impl(48.0f), Dp.constructor-impl(12.0f));
            Object objRememberedValue16 = composer3.rememberedValue();
            if (objRememberedValue16 == companion.getEmpty()) {
                objRememberedValue16 = new pz1(11, mutableState);
                composer3.updateRememberedValue(objRememberedValue16);
            }
            Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifier2, (Function1) objRememberedValue16);
            Arrangement arrangement = Arrangement.INSTANCE;
            FocusRequester focusRequester11 = focusRequester5;
            MeasurePolicy measurePolicyRowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement.spacedBy-0680j_4(Dp.constructor-impl(12.0f)), companion2.getCenterVertically(), composer3, 54);
            long currentCompositeKeyHashCode2 = ComposablesKt.getCurrentCompositeKeyHashCode(composer3, 0);
            int i17 = (int) (currentCompositeKeyHashCode2 ^ (currentCompositeKeyHashCode2 >>> 32));
            CompositionLocalMap currentCompositionLocalMap2 = composer3.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer3, modifierOnFocusChanged);
            Function0 constructor2 = companion3.getConstructor();
            if (composer3.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composer3.startReusableNode();
            if (composer3.getInserting()) {
                composer3.createNode(constructor2);
            } else {
                composer3.useNode();
            }
            Composer composer5 = Updater.constructor-impl(composer3);
            zr.z(companion3, composer5, measurePolicyRowMeasurePolicy, composer5, currentCompositionLocalMap2);
            Updater.set-impl(composer5, modifierMaterializeModifier2, pd.h(companion3, composer5, Integer.valueOf(i17), composer5));
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            int i18 = r25 >> 12;
            int i19 = r25 << 12;
            b(str, str2, null, function0, function3, function4, e(mutableState2), z2, focusRequester11, function8, null, composer3, (i18 & 458752) | (i18 & 14) | 100663296 | (i18 & 112) | ((r25 >> 9) & 7168) | (i18 & 57344) | (i19 & 29360128) | ((i14 << 21) & 1879048192), 0, GL20.GL_FRONT);
            if (z4) {
                composer3.startReplaceGroup(1964352223);
                IconKt.Icon-ww6aTOc(VpnKeyKt.getVpnKey(Icons.INSTANCE.getDefault()), "代理已启用", SizeKt.size-3ABfNKs(companion4, Dp.constructor-impl(24.0f)), c, composer3, 3504, 0);
                composer3.endReplaceGroup();
            } else if (z5) {
                composer3.startReplaceGroup(1964647064);
                IconKt.Icon-ww6aTOc(VpnKeyKt.getVpnKey(Icons.INSTANCE.getDefault()), "代理已禁用（仅直连）", SizeKt.size-3ABfNKs(companion4, Dp.constructor-impl(24.0f)), d, composer3, 3504, 0);
                composer3.endReplaceGroup();
            } else if (z6) {
                composer3.startReplaceGroup(1964945749);
                IconKt.Icon-ww6aTOc(VpnKeyKt.getVpnKey(Icons.INSTANCE.getDefault()), "代理已禁用（局域网不代理）", SizeKt.size-3ABfNKs(companion4, Dp.constructor-impl(24.0f)), d, composer3, 3504, 0);
                composer3.endReplaceGroup();
            } else {
                composer3.startReplaceGroup(1965192199);
                composer3.endReplaceGroup();
            }
            SpacerKt.Spacer(ru1.E(rowScopeInstance, companion4, 1.0f, false, 2, (Object) null), composer3, 0);
            MeasurePolicy measurePolicyRowMeasurePolicy2 = RowKt.rowMeasurePolicy(arrangement.spacedBy-0680j_4(Dp.constructor-impl(12.0f)), companion2.getCenterVertically(), composer3, 54);
            long currentCompositeKeyHashCode3 = ComposablesKt.getCurrentCompositeKeyHashCode(composer3, 0);
            int i20 = (int) (currentCompositeKeyHashCode3 ^ (currentCompositeKeyHashCode3 >>> 32));
            CompositionLocalMap currentCompositionLocalMap3 = composer3.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composer3, companion4);
            Function0 constructor3 = companion3.getConstructor();
            if (composer3.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composer3.startReusableNode();
            if (composer3.getInserting()) {
                composer3.createNode(constructor3);
            } else {
                composer3.useNode();
            }
            Composer composer6 = Updater.constructor-impl(composer3);
            zr.z(companion3, composer6, measurePolicyRowMeasurePolicy2, composer6, currentCompositionLocalMap3);
            Updater.set-impl(composer6, modifierMaterializeModifier3, pd.h(companion3, composer6, Integer.valueOf(i20), composer6));
            Icons icons = Icons.INSTANCE;
            ImageVector videoLibrary = VideoLibraryKt.getVideoLibrary(icons.getDefault());
            to0 to0Var3 = to0.a;
            int i21 = 805309446 | (i19 & 57344) | (i19 & 458752);
            int i22 = r25 << 15;
            int i23 = i22 & 29360128;
            int i24 = i22 & 234881024;
            to0 to0Var4 = to0Var2;
            Composer composer7 = composer3;
            a("媒体", videoLibrary, to0Var2 == to0Var3, to0Var3, to0Var4, function1, function5, z, z2, focusRequester4, ((Boolean) mutableState2.getValue()).booleanValue(), null, composer7, i21 | ((i13 << 18) & 3670016) | i23 | i24, 0, GL30.GL_COLOR);
            ImageVector favorite = FavoriteKt.getFavorite(icons.getDefault());
            to0 to0Var5 = to0.b;
            int i25 = i21 | i23 | i24;
            a("收藏", favorite, to0Var4 == to0Var5, to0Var5, to0Var4, function1, null, z, z2, focusRequester2, ((Boolean) mutableState2.getValue()).booleanValue(), null, composer7, i25, 0, 6208);
            ImageVector search = SearchKt.getSearch(icons.getDefault());
            to0 to0Var6 = to0.c;
            a("搜索", search, to0Var4 == to0Var6, to0Var6, to0Var4, function1, null, z, z2, focusRequester3, ((Boolean) mutableState2.getValue()).booleanValue(), null, composer7, i25, 0, 6208);
            c(SettingsKt.getSettings(icons.getDefault()), function2, ((Boolean) mutableState2.getValue()).booleanValue(), z2, z7, focusRequester10, null, composer7, ((i12 >> 15) & 896) | 1572912 | (i15 & 57344) | (i13 & 458752));
            composer2 = composer7;
            if (f22.w(composer2)) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: ta2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i4 | 1);
                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i5);
                    int iUpdateChangedFlags3 = RecomposeScopeImplKt.updateChangedFlags(i6);
                    ya2.d(to0Var, function1, z, z2, str, str2, function0, function2, function3, function4, function5, z3, z4, z5, z6, z7, focusRequester, i, i2, i3, function6, function7, function8, modifier, (Composer) obj, iUpdateChangedFlags, iUpdateChangedFlags2, iUpdateChangedFlags3);
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public static final boolean e(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }
}
