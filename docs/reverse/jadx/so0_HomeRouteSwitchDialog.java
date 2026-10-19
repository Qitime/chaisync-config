package defpackage;

import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotMutationPolicy;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.window.AndroidDialog_androidKt;
import androidx.compose.ui.window.DialogProperties;
import androidx.media3.extractor.ts.PsExtractor;
import androidx.profileinstaller.ProfileVerifier;
import androidx.tv.material3.Border;
import androidx.tv.material3.ClickableSurfaceDefaults;
import androidx.tv.material3.SurfaceKt;
import com.badlogic.gdx.graphics.GL20;
import com.dh.myembyapp.data.model.ItemCounts;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import kotlin.Function;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* JADX INFO: loaded from: D:\Ai\Github\ChaiSync-GH\build\decoded\classes4.dex */
public abstract class so0 {
    public static final void a(ItemCounts itemCounts, boolean z, Function0 function0, Function0 function1, Function0 function2, Function0 function3, Composer composer, int i) {
        ItemCounts itemCounts2;
        int i2;
        Function0 function4;
        Function0 function5;
        Function0 function6;
        function0.getClass();
        function1.getClass();
        function2.getClass();
        function3.getClass();
        Composer composerStartRestartGroup = composer.startRestartGroup(-760364776);
        if ((i & 6) == 0) {
            itemCounts2 = itemCounts;
            i2 = (composerStartRestartGroup.changed(itemCounts2) ? 4 : 2) | i;
        } else {
            itemCounts2 = itemCounts;
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            function4 = function0;
            i2 |= composerStartRestartGroup.changedInstance(function4) ? 256 : 128;
        } else {
            function4 = function0;
        }
        if ((i & 3072) == 0) {
            function5 = function1;
            i2 |= composerStartRestartGroup.changedInstance(function5) ? 2048 : 1024;
        } else {
            function5 = function1;
        }
        if ((i & 24576) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(function2) ? GL20.GL_COLOR_BUFFER_BIT : 8192;
        }
        if ((196608 & i) == 0) {
            function6 = function3;
            i2 |= composerStartRestartGroup.changedInstance(function6) ? ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ : 65536;
        } else {
            function6 = function3;
        }
        if (composerStartRestartGroup.shouldExecute((74899 & i2) != 74898, i2 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-760364776, i2, -1, "com.dh.myembyapp.ui.components.HomeMediaMenuDialog (HomeMediaMenuDialog.kt:90)");
            }
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                objRememberedValue = zr.n(composerStartRestartGroup);
            }
            FocusRequester focusRequester = (FocusRequester) objRememberedValue;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            boolean z2 = (i2 & 112) == 32;
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (z2 || objRememberedValue2 == companion.getEmpty()) {
                objRememberedValue2 = zr.m(z, (SnapshotMutationPolicy) null, 2, (Object) null, composerStartRestartGroup);
            }
            MutableState mutableState = (MutableState) objRememberedValue2;
            Unit unit = Unit.INSTANCE;
            Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue3 == companion.getEmpty()) {
                objRememberedValue3 = new oi(focusRequester, null, 7);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
            }
            EffectsKt.LaunchedEffect(unit, (Function2) objRememberedValue3, composerStartRestartGroup, 6);
            Function0 function7 = function5;
            AndroidDialog_androidKt.Dialog(function6, new DialogProperties(true, false, false), ComposableLambdaKt.rememberComposableLambda(-1501629855, true, new oo0(mutableState, g81Var, focusRequester, function4, function7, function2, itemCounts2), composerStartRestartGroup, 54), composerStartRestartGroup, ((i2 >> 15) & 14) | 432, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerStartRestartGroup.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composerStartRestartGroup.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new up(itemCounts, z, function0, function1, function2, function3, i));
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x004f  */
    /* JADX WARN: Code duplicated, block: B:32:0x0057  */
    /* JADX WARN: Code duplicated, block: B:33:0x005a  */
    /* JADX WARN: Code duplicated, block: B:35:0x005e  */
    /* JADX WARN: Code duplicated, block: B:38:0x0067  */
    /* JADX WARN: Code duplicated, block: B:39:0x0069  */
    /* JADX WARN: Code duplicated, block: B:42:0x0072 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:43:0x0074  */
    /* JADX WARN: Code duplicated, block: B:44:0x0077  */
    /* JADX WARN: Code duplicated, block: B:47:0x007f  */
    /* JADX WARN: Code duplicated, block: B:50:0x008a  */
    /* JADX WARN: Code duplicated, block: B:53:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:56:0x0144  */
    /* JADX WARN: Code duplicated, block: B:58:0x014a  */
    /* JADX WARN: Code duplicated, block: B:61:0x0156  */
    /* JADX WARN: Code duplicated, block: B:63:? A[RETURN, SYNTHETIC] */
    public static final void b(String str, ImageVector imageVector, Modifier modifier, Function0 function0, Composer composer, int i, int i2) {
        int i3;
        Modifier modifier2;
        boolean z;
        Composer composer2;
        Modifier modifier3;
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup;
        Modifier modifier4;
        int i4;
        Composer composerStartRestartGroup = composer.startRestartGroup(-817361997);
        int i5 = 4;
        if ((i & 6) == 0) {
            i3 = (composerStartRestartGroup.changed(str) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerStartRestartGroup.changed(imageVector) ? 32 : 16;
        }
        int i6 = i2 & 4;
        if (i6 == 0) {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerStartRestartGroup.changed(modifier2) ? 256 : 128;
            }
            if ((i & 3072) != 0) {
                if (composerStartRestartGroup.changedInstance(function0)) {
                    i4 = 2048;
                } else {
                    i4 = 1024;
                }
                i3 |= i4;
            }
            if ((i3 & 1171) != 1170) {
                z = true;
            } else {
                z = false;
            }
            if (composerStartRestartGroup.shouldExecute(z, i3 & 1)) {
                if (i6 != 0) {
                    modifier4 = Modifier.Companion;
                } else {
                    modifier4 = modifier2;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-817361997, i3, -1, "com.dh.myembyapp.ui.components.HomeMediaMenuRow (HomeMediaMenuDialog.kt:329)");
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
                }
                g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                Modifier modifier5 = SizeKt.height-3ABfNKs(SizeKt.fillMaxWidth$default(modifier4, 0.0f, 1, (Object) null), Dp.constructor-impl(52.0f));
                ClickableSurfaceDefaults clickableSurfaceDefaults = ClickableSurfaceDefaults.INSTANCE;
                long j = g81Var.w;
                long j2 = g81Var.x;
                long j3 = g81Var.A;
                int i7 = ClickableSurfaceDefaults.$stable;
                Modifier modifier6 = modifier4;
                composer2 = composerStartRestartGroup;
                SurfaceKt.m632Surface05tvjtU(function0, modifier5, null, false, 0.0f, null, clickableSurfaceDefaults.m401colorsoq7We08(j, j3, j2, j3, 0L, 0L, 0L, 0L, composerStartRestartGroup, i7 << 24, PsExtractor.VIDEO_STREAM_MASK), ClickableSurfaceDefaults.scale$default(clickableSurfaceDefaults, 0.0f, 1.02f, 0.0f, 0.0f, 0.0f, 29, null), clickableSurfaceDefaults.border(null, new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null), null, null, null, composerStartRestartGroup, i7 << 15, 29), im0.c(g81Var), null, ComposableLambdaKt.rememberComposableLambda(-1888480686, true, new wh(imageVector, g81Var, str, i5), composerStartRestartGroup, 54), composer2, (i3 >> 9) & 14, 48, 1084);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier3 = modifier6;
            } else {
                composer2 = composerStartRestartGroup;
                composer2.skipToGroupEnd();
                modifier3 = modifier2;
            }
            scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
            if (scopeUpdateScopeEndRestartGroup != null) {
                scopeUpdateScopeEndRestartGroup.updateScope(new d2(str, imageVector, modifier3, function0, i, i2));
            }
        }
        i3 |= 384;
        modifier2 = modifier;
        if ((i & 3072) != 0) {
            if (composerStartRestartGroup.changedInstance(function0)) {
                i4 = 2048;
            } else {
                i4 = 1024;
            }
            i3 |= i4;
        }
        if ((i3 & 1171) != 1170) {
            z = true;
        } else {
            z = false;
        }
        if (composerStartRestartGroup.shouldExecute(z, i3 & 1)) {
            if (i6 != 0) {
                modifier4 = Modifier.Companion;
            } else {
                modifier4 = modifier2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-817361997, i3, -1, "com.dh.myembyapp.ui.components.HomeMediaMenuRow (HomeMediaMenuDialog.kt:329)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var2 = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Modifier modifier7 = SizeKt.height-3ABfNKs(SizeKt.fillMaxWidth$default(modifier4, 0.0f, 1, (Object) null), Dp.constructor-impl(52.0f));
            ClickableSurfaceDefaults clickableSurfaceDefaults2 = ClickableSurfaceDefaults.INSTANCE;
            long j4 = g81Var2.w;
            long j5 = g81Var2.x;
            long j6 = g81Var2.A;
            int i8 = ClickableSurfaceDefaults.$stable;
            Modifier modifier8 = modifier4;
            composer2 = composerStartRestartGroup;
            SurfaceKt.m632Surface05tvjtU(function0, modifier7, null, false, 0.0f, null, clickableSurfaceDefaults2.m401colorsoq7We08(j4, j6, j5, j6, 0L, 0L, 0L, 0L, composerStartRestartGroup, i8 << 24, PsExtractor.VIDEO_STREAM_MASK), ClickableSurfaceDefaults.scale$default(clickableSurfaceDefaults2, 0.0f, 1.02f, 0.0f, 0.0f, 0.0f, 29, null), clickableSurfaceDefaults2.border(null, new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var2.b), 0.0f, null, 6, null), null, null, null, composerStartRestartGroup, i8 << 15, 29), im0.c(g81Var2), null, ComposableLambdaKt.rememberComposableLambda(-1888480686, true, new wh(imageVector, g81Var2, str, i5), composerStartRestartGroup, 54), composer2, (i3 >> 9) & 14, 48, 1084);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier3 = modifier8;
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
            modifier3 = modifier2;
        }
        scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new d2(str, imageVector, modifier3, function0, i, i2));
        }
    }

    public static final void c(final ItemCounts itemCounts, Composer composer, final int i) {
        Composer composer2;
        Composer composerStartRestartGroup = composer.startRestartGroup(-656427186);
        int i2 = (i & 6) == 0 ? (composerStartRestartGroup.changed(itemCounts) ? 4 : 2) | i : i;
        final int i3 = 0;
        final int i4 = 1;
        if (composerStartRestartGroup.shouldExecute((i2 & 3) != 2, i2 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-656427186, i2, -1, "com.dh.myembyapp.ui.components.HomeMediaMenuStatsSection (HomeMediaMenuDialog.kt:176)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            if (itemCounts == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composerStartRestartGroup.endRestartGroup();
                if (scopeUpdateScopeEndRestartGroup != null) {
                    scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: po0
                        public final Object invoke(Object obj, Object obj2) {
                            int i5 = i3;
                            Composer composer3 = (Composer) obj;
                            ((Integer) obj2).intValue();
                            switch (i5) {
                                case 0:
                                    so0.c(itemCounts, composer3, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                case 1:
                                    so0.c(itemCounts, composer3, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                default:
                                    so0.c(itemCounts, composer3, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    });
                    return;
                }
                return;
            }
            if (itemCounts.getMovieCount() == 0 && itemCounts.getSeriesCount() == 0 && itemCounts.getEpisodeCount() == 0) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                ScopeUpdateScope scopeUpdateScopeEndRestartGroup2 = composerStartRestartGroup.endRestartGroup();
                if (scopeUpdateScopeEndRestartGroup2 != null) {
                    scopeUpdateScopeEndRestartGroup2.updateScope(new Function2() { // from class: po0
                        public final Object invoke(Object obj, Object obj2) {
                            int i5 = i4;
                            Composer composer3 = (Composer) obj;
                            ((Integer) obj2).intValue();
                            switch (i5) {
                                case 0:
                                    so0.c(itemCounts, composer3, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                case 1:
                                    so0.c(itemCounts, composer3, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                default:
                                    so0.c(itemCounts, composer3, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    });
                    return;
                }
                return;
            }
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                objRememberedValue = NumberFormat.getIntegerInstance(Locale.US);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
            }
            NumberFormat numberFormat = (NumberFormat) objRememberedValue;
            boolean z = (i2 & 14) == 4;
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (z || objRememberedValue2 == companion.getEmpty()) {
                objRememberedValue2 = CollectionsKt.listOf((Object[]) new Triple[]{new Triple(numberFormat.format(Integer.valueOf(itemCounts.getMovieCount())), "电影", Color.box-impl(ColorKt.Color(4293417819L))), new Triple(numberFormat.format(Integer.valueOf(itemCounts.getSeriesCount())), "剧集", Color.box-impl(ColorKt.Color(4287397113L))), new Triple(numberFormat.format(Integer.valueOf(itemCounts.getEpisodeCount())), "集数", Color.box-impl(ColorKt.Color(4284385023L)))});
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            List<Triple> list = (List) objRememberedValue2;
            Modifier.Companion companion2 = Modifier.Companion;
            Modifier modifier = PaddingKt.padding-qDBjuR0$default(SizeKt.fillMaxWidth$default(companion2, 0.0f, 1, (Object) null), 0.0f, Dp.constructor-impl(4.0f), 0.0f, 0.0f, 13, (Object) null);
            Arrangement arrangement = Arrangement.INSTANCE;
            Arrangement.HorizontalOrVertical horizontalOrVertical = arrangement.spacedBy-0680j_4(Dp.constructor-impl(10.0f));
            Alignment.Companion companion3 = Alignment.Companion;
            MeasurePolicy measurePolicyColumnMeasurePolicy = ColumnKt.columnMeasurePolicy(horizontalOrVertical, companion3.getStart(), composerStartRestartGroup, 6);
            long currentCompositeKeyHashCode = ComposablesKt.getCurrentCompositeKeyHashCode(composerStartRestartGroup, 0);
            int i5 = (int) (currentCompositeKeyHashCode ^ (currentCompositeKeyHashCode >>> 32));
            CompositionLocalMap currentCompositionLocalMap = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifier);
            ComposeUiNode.Companion companion4 = ComposeUiNode.Companion;
            Function0 constructor = companion4.getConstructor();
            if (composerStartRestartGroup.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composerStartRestartGroup.startReusableNode();
            if (composerStartRestartGroup.getInserting()) {
                composerStartRestartGroup.createNode(constructor);
            } else {
                composerStartRestartGroup.useNode();
            }
            Composer composer3 = Updater.constructor-impl(composerStartRestartGroup);
            zr.z(companion4, composer3, measurePolicyColumnMeasurePolicy, composer3, currentCompositionLocalMap);
            Updater.set-impl(composer3, modifierMaterializeModifier, pd.h(companion4, composer3, Integer.valueOf(i5), composer3));
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            g81 g81Var2 = g81Var;
            TextKt.Text-Nvy7gAk("媒体数量", (Modifier) null, g81Var.B, (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, MaterialTheme.INSTANCE.getTypography(composerStartRestartGroup, MaterialTheme.$stable).getLabelLarge(), composerStartRestartGroup, 6, 0, 131066);
            Composer composer4 = composerStartRestartGroup;
            Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(companion2, 0.0f, 1, (Object) null);
            MeasurePolicy measurePolicyRowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement.spacedBy-0680j_4(Dp.constructor-impl(10.0f)), companion3.getTop(), composer4, 6);
            int i6 = 0;
            long currentCompositeKeyHashCode2 = ComposablesKt.getCurrentCompositeKeyHashCode(composer4, 0);
            int i7 = (int) (currentCompositeKeyHashCode2 ^ (currentCompositeKeyHashCode2 >>> 32));
            CompositionLocalMap currentCompositionLocalMap2 = composer4.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer4, modifierFillMaxWidth$default);
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
            Updater.set-impl(composer5, modifierMaterializeModifier2, pd.h(companion4, composer5, Integer.valueOf(i7), composer5));
            RowScope rowScope = RowScopeInstance.INSTANCE;
            composer4.startReplaceGroup(-1017804747);
            for (Triple triple : list) {
                String str = (String) triple.component1();
                String str2 = (String) triple.component2();
                long j = ((Color) triple.component3()).unbox-impl();
                Modifier modifierE = ru1.E(rowScope, Modifier.Companion, 1.0f, false, 2, (Object) null);
                RowScope rowScope2 = rowScope;
                g81 g81Var3 = g81Var2;
                Modifier modifierV = bc0.v(16.0f, modifierE, g81Var3.C);
                float f = Dp.constructor-impl(1.0f);
                MaterialTheme materialTheme = MaterialTheme.INSTANCE;
                int i8 = MaterialTheme.$stable;
                Modifier modifier2 = PaddingKt.padding-VpY3zN4(BorderKt.border-xT4_qwU(modifierV, f, materialTheme.getColorScheme(composer4, i8).getOutlineVariant-0d7_KjU(), RoundedCornerShapeKt.RoundedCornerShape-0680j_4(Dp.constructor-impl(16.0f))), Dp.constructor-impl(10.0f), Dp.constructor-impl(10.0f));
                MeasurePolicy measurePolicyP = zr.p(3.0f, Arrangement.INSTANCE, Alignment.Companion.getCenterHorizontally(), composer4, 54);
                long currentCompositeKeyHashCode3 = ComposablesKt.getCurrentCompositeKeyHashCode(composer4, i6);
                int i9 = (int) (currentCompositeKeyHashCode3 ^ (currentCompositeKeyHashCode3 >>> 32));
                CompositionLocalMap currentCompositionLocalMap3 = composer4.getCurrentCompositionLocalMap();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composer4, modifier2);
                ComposeUiNode.Companion companion5 = ComposeUiNode.Companion;
                Function0 constructor3 = companion5.getConstructor();
                if (composer4.getApplier() == null) {
                    ComposablesKt.invalidApplier();
                }
                composer4.startReusableNode();
                if (composer4.getInserting()) {
                    composer4.createNode(constructor3);
                } else {
                    composer4.useNode();
                }
                Composer composer6 = Updater.constructor-impl(composer4);
                zr.z(companion5, composer6, measurePolicyP, composer6, currentCompositionLocalMap3);
                Updater.set-impl(composer6, modifierMaterializeModifier3, pd.h(companion5, composer6, Integer.valueOf(i9), composer6));
                ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                str.getClass();
                Composer composer7 = composer4;
                TextKt.Text-Nvy7gAk(str, (Modifier) null, j, (TextAutoSize) null, TextUnitKt.getSp(18), (FontStyle) null, FontWeight.Companion.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, (TextStyle) null, composer7, 1597440, 0, 262058);
                TextKt.Text-Nvy7gAk(str2, (Modifier) null, Color.copy-wmQWz5c$default(materialTheme.getColorScheme(composer7, i8).getOnSurface-0d7_KjU(), 0.72f, 0.0f, 0.0f, 0.0f, 14, (Object) null), (TextAutoSize) null, TextUnitKt.getSp(13), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, (TextStyle) null, composer7, 24576, 0, 262122);
                composer7.endNode();
                composer4 = composer7;
                rowScope = rowScope2;
                g81Var2 = g81Var3;
                i6 = 0;
            }
            composer2 = composer4;
            composer2.endReplaceGroup();
            composer2.endNode();
            composer2.endNode();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup3 = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup3 != null) {
            final int i10 = 2;
            scopeUpdateScopeEndRestartGroup3.updateScope(new Function2() { // from class: po0
                public final Object invoke(Object obj, Object obj2) {
                    int i11 = i10;
                    Composer composer8 = (Composer) obj;
                    ((Integer) obj2).intValue();
                    switch (i11) {
                        case 0:
                            so0.c(itemCounts, composer8, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                        case 1:
                            so0.c(itemCounts, composer8, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                        default:
                            so0.c(itemCounts, composer8, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public static final void d(String str, List list, boolean z, Function1 function1, Function0 function0, Composer composer, int i, int i2) {
        String str2;
        int i3;
        boolean z2;
        Function1 function2;
        Composer composer2;
        String str3;
        list.getClass();
        function1.getClass();
        function0.getClass();
        Composer composerStartRestartGroup = composer.startRestartGroup(1262486505);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
            str2 = str;
        } else if ((i & 6) == 0) {
            str2 = str;
            i3 = (composerStartRestartGroup.changed(str2) ? 4 : 2) | i;
        } else {
            str2 = str;
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(list) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            z2 = z;
            i3 |= composerStartRestartGroup.changed(z2) ? 256 : 128;
        } else {
            z2 = z;
        }
        if ((i & 3072) == 0) {
            function2 = function1;
            i3 |= composerStartRestartGroup.changedInstance(function2) ? 2048 : 1024;
        } else {
            function2 = function1;
        }
        if ((i & 24576) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(function0) ? GL20.GL_COLOR_BUFFER_BIT : 8192;
        }
        int i5 = i3;
        if (composerStartRestartGroup.shouldExecute((i5 & 9363) != 9362, i5 & 1)) {
            String str4 = i4 != 0 ? "切换线路" : str2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1262486505, i5, -1, "com.dh.myembyapp.ui.components.HomeRouteSwitchDialog (HomeMediaMenuDialog.kt:250)");
            }
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                objRememberedValue = zr.n(composerStartRestartGroup);
            }
            FocusRequester focusRequester = (FocusRequester) objRememberedValue;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            boolean zChanged = composerStartRestartGroup.changed(list);
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (zChanged || objRememberedValue2 == companion.getEmpty()) {
                vo0 vo0Var = (vo0) CollectionsKt.firstOrNull(list);
                objRememberedValue2 = vo0Var != null ? vo0Var.a : null;
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            String str5 = (String) objRememberedValue2;
            Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue3 == companion.getEmpty()) {
                objRememberedValue3 = new oi(focusRequester, null, 8);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
            }
            EffectsKt.LaunchedEffect(str5, (Function2) objRememberedValue3, composerStartRestartGroup, 0);
            String str6 = str4;
            composer2 = composerStartRestartGroup;
            AndroidDialog_androidKt.Dialog(function0, new DialogProperties(true, false, false), ComposableLambdaKt.rememberComposableLambda(-954526848, true, new e80(g81Var, str6, list, z2, str5, function2, focusRequester), composerStartRestartGroup, 54), composer2, ((i5 >> 12) & 14) | 432, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            str3 = str6;
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
            str3 = str2;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new c20(str3, list, z, function1, function0, i, i2));
        }
    }

    public static final void e(vo0 vo0Var, boolean z, Modifier modifier, Function0 function0, Composer composer, int i) {
        int i2;
        Composer composer2;
        Composer composerStartRestartGroup = composer.startRestartGroup(-1676762659);
        if ((i & 6) == 0) {
            i2 = (composerStartRestartGroup.changed(vo0Var) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changed(z) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerStartRestartGroup.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(function0) ? 2048 : 1024;
        }
        if (composerStartRestartGroup.shouldExecute((i2 & 1171) != 1170, i2 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1676762659, i2, -1, "com.dh.myembyapp.ui.components.HomeRouteSwitchRow (HomeMediaMenuDialog.kt:376)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Modifier modifier2 = SizeKt.height-3ABfNKs(SizeKt.fillMaxWidth$default(modifier, 0.0f, 1, (Object) null), Dp.constructor-impl(58.0f));
            ClickableSurfaceDefaults clickableSurfaceDefaults = ClickableSurfaceDefaults.INSTANCE;
            boolean z2 = vo0Var.d;
            long j = z2 ? g81Var.y : g81Var.w;
            long j2 = z2 ? g81Var.z : g81Var.x;
            long j3 = j;
            long j4 = g81Var.A;
            int i3 = ClickableSurfaceDefaults.$stable;
            composer2 = composerStartRestartGroup;
            SurfaceKt.m632Surface05tvjtU(function0, modifier2, null, false, 0.0f, null, clickableSurfaceDefaults.m401colorsoq7We08(j3, j4, j2, j4, 0L, 0L, 0L, 0L, composerStartRestartGroup, i3 << 24, PsExtractor.VIDEO_STREAM_MASK), ClickableSurfaceDefaults.scale$default(clickableSurfaceDefaults, 0.0f, 1.02f, 0.0f, 0.0f, 0.0f, 29, null), clickableSurfaceDefaults.border(null, new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null), null, null, null, composerStartRestartGroup, i3 << 15, 29), im0.c(g81Var), null, ComposableLambdaKt.rememberComposableLambda(-1429292450, true, new l70(vo0Var, g81Var, z), composerStartRestartGroup, 54), composer2, (i2 >> 9) & 14, 48, 1084);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new b8((Object) vo0Var, z, (Object) modifier, (Function) function0, i, 4));
        }
    }
}
