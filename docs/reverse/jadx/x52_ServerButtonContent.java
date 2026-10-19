package defpackage;

import android.graphics.Bitmap;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusChangedModifierKt;
import androidx.compose.ui.graphics.AndroidImageBitmap_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import androidx.media3.extractor.text.ttml.TtmlNode;
import androidx.tv.material3.ButtonKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* JADX INFO: loaded from: D:\Ai\Github\ChaiSync-GH\build\decoded\classes4.dex */
public final /* synthetic */ class x52 implements Function3 {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ String b;
    public final /* synthetic */ boolean c;
    public final /* synthetic */ boolean d;
    public final /* synthetic */ MutableState e;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    public /* synthetic */ x52(Bitmap bitmap, String str, boolean z, boolean z2, Function0 function0, MutableState mutableState) {
        this.g = bitmap;
        this.b = str;
        this.c = z;
        this.d = z2;
        this.h = function0;
        this.e = mutableState;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Composer composer;
        long jU;
        int i = this.a;
        MutableState mutableState = this.e;
        boolean z = this.d;
        boolean z2 = this.c;
        Object obj4 = this.h;
        Object obj5 = this.g;
        switch (i) {
            case 0:
                Bitmap bitmap = (Bitmap) obj5;
                Function0 function0 = (Function0) obj4;
                Composer composer2 = (Composer) obj2;
                int iIntValue = ((Integer) obj3).intValue();
                ((ColumnScope) obj).getClass();
                if (composer2.shouldExecute((iIntValue & 17) != 16, iIntValue & 1)) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(293824569, iIntValue, -1, "com.dh.myembyapp.ui.components.SubtitleFontUploadDialog.<anonymous>.<anonymous>.<anonymous> (SubtitleFontUploadDialog.kt:61)");
                    }
                    Modifier.Companion companion = Modifier.Companion;
                    Modifier modifier = PaddingKt.padding-3ABfNKs(SizeKt.fillMaxWidth$default(companion, 0.0f, 1, (Object) null), Dp.constructor-impl(20.0f));
                    MeasurePolicy measurePolicyColumnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.INSTANCE.spacedBy-0680j_4(Dp.constructor-impl(16.0f)), Alignment.Companion.getCenterHorizontally(), composer2, 54);
                    long currentCompositeKeyHashCode = ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0);
                    int i2 = (int) (currentCompositeKeyHashCode ^ (currentCompositeKeyHashCode >>> 32));
                    CompositionLocalMap currentCompositionLocalMap = composer2.getCurrentCompositionLocalMap();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifier);
                    ComposeUiNode.Companion companion2 = ComposeUiNode.Companion;
                    Function0 constructor = companion2.getConstructor();
                    if (composer2.getApplier() == null) {
                        ComposablesKt.invalidApplier();
                    }
                    composer2.startReusableNode();
                    if (composer2.getInserting()) {
                        composer2.createNode(constructor);
                    } else {
                        composer2.useNode();
                    }
                    Composer composer3 = Updater.constructor-impl(composer2);
                    zr.z(companion2, composer3, measurePolicyColumnMeasurePolicy, composer3, currentCompositionLocalMap);
                    Updater.set-impl(composer3, modifierMaterializeModifier, pd.h(companion2, composer3, Integer.valueOf(i2), composer3));
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    MaterialTheme materialTheme = MaterialTheme.INSTANCE;
                    int i3 = MaterialTheme.$stable;
                    TextKt.Text-Nvy7gAk("手机扫码上传字体", (Modifier) null, materialTheme.getColorScheme(composer2, i3).getPrimary-0d7_KjU(), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer2, i3).getTitleMedium(), composer2, 6, 0, 131066);
                    if (bitmap != null) {
                        composer2.startReplaceGroup(302991543);
                        ImageKt.m0Image5hnEew(AndroidImageBitmap_androidKt.asImageBitmap(bitmap), "上传字体二维码", SizeKt.size-3ABfNKs(companion, Dp.constructor-impl(220.0f)), null, null, 0.0f, null, 0, composer2, 432, 248);
                        String str = this.b;
                        if (str == null) {
                            str = TtmlNode.ANONYMOUS_REGION_ID;
                        }
                        TextKt.Text-Nvy7gAk(str, (Modifier) null, Color.copy-wmQWz5c$default(materialTheme.getColorScheme(composer2, i3).getOnSurface-0d7_KjU(), 0.7f, 0.0f, 0.0f, 0.0f, 14, (Object) null), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer2, i3).getBodySmall(), composer2, 0, 0, 131066);
                        TextKt.Text-Nvy7gAk("在手机浏览器打开上传页面，支持 ttf/otf", (Modifier) null, Color.copy-wmQWz5c$default(materialTheme.getColorScheme(composer2, i3).getOnSurface-0d7_KjU(), 0.7f, 0.0f, 0.0f, 0.0f, 14, (Object) null), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer2, i3).getBodySmall(), composer2, 6, 0, 131066);
                        composer2.endReplaceGroup();
                    } else if (z2) {
                        composer2.startReplaceGroup(303820669);
                        TextKt.Text-Nvy7gAk("无法启动上传服务，请检查网络连接", (Modifier) null, materialTheme.getColorScheme(composer2, i3).getError-0d7_KjU(), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer2, i3).getBodySmall(), composer2, 6, 0, 131066);
                        composer2.endReplaceGroup();
                    } else if (z) {
                        composer2.startReplaceGroup(304114859);
                        TextKt.Text-Nvy7gAk("正在启动上传服务...", (Modifier) null, Color.copy-wmQWz5c$default(materialTheme.getColorScheme(composer2, i3).getOnSurface-0d7_KjU(), 0.7f, 0.0f, 0.0f, 0.0f, 14, (Object) null), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer2, i3).getBodySmall(), composer2, 6, 0, 131066);
                        composer2.endReplaceGroup();
                    } else {
                        composer2.startReplaceGroup(304409669);
                        TextKt.Text-Nvy7gAk("无法启动上传服务", (Modifier) null, materialTheme.getColorScheme(composer2, i3).getError-0d7_KjU(), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer2, i3).getBodySmall(), composer2, 6, 0, 131066);
                        composer2.endReplaceGroup();
                    }
                    Object objRememberedValue = composer2.rememberedValue();
                    if (objRememberedValue == Composer.Companion.getEmpty()) {
                        objRememberedValue = new pz1(5, mutableState);
                        composer2.updateRememberedValue(objRememberedValue);
                    }
                    ButtonKt.m348ButtonTCVpFMg(function0, FocusChangedModifierKt.onFocusChanged(companion, (Function1) objRememberedValue), null, false, be2.l(1.05f), null, null, be2.i(de2.c, false, composer2, 6, 2), 0.0f, be2.h(composer2), null, null, ComposableLambdaKt.rememberComposableLambda(-192207725, true, new ib1(17, mutableState), composer2, 54), composer2, 0, 384, 3436);
                    composer2.endNode();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                } else {
                    composer2.skipToGroupEnd();
                }
                break;
            default:
                Integer num = (Integer) obj5;
                String str2 = (String) obj4;
                Composer composer4 = (Composer) obj2;
                int iIntValue2 = ((Integer) obj3).intValue();
                ((RowScope) obj).getClass();
                if (composer4.shouldExecute((iIntValue2 & 17) != 16, iIntValue2 & 1)) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1144869446, iIntValue2, -1, "com.dh.myembyapp.ui.screens.home.ServerButton.<anonymous> (TopNavigation.kt:583)");
                    }
                    Arrangement.HorizontalOrVertical center = Arrangement.INSTANCE.getCenter();
                    Alignment.Vertical centerVertically = Alignment.Companion.getCenterVertically();
                    Modifier.Companion companion3 = Modifier.Companion;
                    Modifier modifierWrapContentWidth$default = SizeKt.wrapContentWidth$default(companion3, (Alignment.Horizontal) null, false, 3, (Object) null);
                    MeasurePolicy measurePolicyRowMeasurePolicy = RowKt.rowMeasurePolicy(center, centerVertically, composer4, 54);
                    long currentCompositeKeyHashCode2 = ComposablesKt.getCurrentCompositeKeyHashCode(composer4, 0);
                    int i4 = (int) (currentCompositeKeyHashCode2 ^ (currentCompositeKeyHashCode2 >>> 32));
                    CompositionLocalMap currentCompositionLocalMap2 = composer4.getCurrentCompositionLocalMap();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer4, modifierWrapContentWidth$default);
                    ComposeUiNode.Companion companion4 = ComposeUiNode.Companion;
                    Function0 constructor2 = companion4.getConstructor();
                    if (composer4.getApplier() == null) {
                        ComposablesKt.invalidApplier();
                    }
                    composer4.startReusableNode();
                    if (composer4.getInserting()) {
                        composer4.createNode(constructor2);
                    } else {
                        composer4.useNode();
                    }
                    Composer composer5 = Updater.constructor-impl(composer4);
                    zr.z(companion4, composer5, measurePolicyRowMeasurePolicy, composer5, currentCompositionLocalMap2);
                    Updater.set-impl(composer5, modifierMaterializeModifier2, pd.h(companion4, composer5, Integer.valueOf(i4), composer5));
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    String str3 = this.b;
                    if (num != null) {
                        composer4.startReplaceGroup(-2015440086);
                        ImageKt.Image(PainterResources_androidKt.painterResource(num.intValue(), composer4, 0), o71.n(str3, " 图标"), SizeKt.size-3ABfNKs(companion3, Dp.constructor-impl(34.0f)), (Alignment) null, ContentScale.Companion.getFit(), 0.0f, (ColorFilter) null, composer4, Painter.$stable | 24960, 104);
                        composer = composer4;
                        composer.endReplaceGroup();
                    } else {
                        composer = composer4;
                        composer.startReplaceGroup(-2015146826);
                        ez1.c(str2, str3 + " 图标", SizeKt.size-3ABfNKs(companion3, Dp.constructor-impl(34.0f)), ContentScale.Companion.getFit(), composer, 3456, 0);
                        composer.endReplaceGroup();
                    }
                    SpacerKt.Spacer(SizeKt.width-3ABfNKs(companion3, Dp.constructor-impl(6.0f)), composer, 6);
                    MaterialTheme materialTheme2 = MaterialTheme.INSTANCE;
                    int i5 = MaterialTheme.$stable;
                    TextStyle titleSmall = materialTheme2.getTypography(composer, i5).getTitleSmall();
                    FontWeight medium = FontWeight.Companion.getMedium();
                    if (((Boolean) mutableState.getValue()).booleanValue() && z2 && z) {
                        composer.startReplaceGroup(-2014574349);
                        composer.endReplaceGroup();
                        jU = ColorKt.Color(4279308561L);
                    } else if (((Boolean) mutableState.getValue()).booleanValue() && z2) {
                        composer.startReplaceGroup(-2014473537);
                        jU = materialTheme2.getColorScheme(composer, i5).getOnSecondary-0d7_KjU();
                        composer.endReplaceGroup();
                    } else {
                        jU = bc0.u(composer, -2014391263, materialTheme2, composer, i5);
                    }
                    TextKt.Text-Nvy7gAk(str3, (Modifier) null, jU, (TextAutoSize) null, 0L, (FontStyle) null, medium, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, TextOverflow.Companion.getEllipsis-gIe3tQ8(), false, 1, 0, (Function1) null, titleSmall, composer, 1572864, 24960, 110522);
                    SpacerKt.Spacer(SizeKt.width-3ABfNKs(companion3, Dp.constructor-impl(4.0f)), composer, 6);
                    composer.endNode();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                } else {
                    composer4.skipToGroupEnd();
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ x52(Integer num, String str, String str2, boolean z, boolean z2, MutableState mutableState) {
        this.g = num;
        this.b = str;
        this.h = str2;
        this.c = z;
        this.d = z2;
        this.e = mutableState;
    }
}
