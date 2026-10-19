package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.activity.compose.BackHandlerKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.foundation.lazy.LazyListState;
import androidx.compose.foundation.lazy.LazyListStateKt;
import androidx.compose.foundation.lazy.d;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.material3.AndroidAlertDialog_androidKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonElevation;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.ProgressIndicatorKt;
import androidx.compose.material3.SliderKt;
import androidx.compose.material3.SliderState;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.DisposableEffectScope;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotMutationPolicy;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.focus.FocusChangedModifierKt;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.window.DialogProperties;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.extractor.text.ttml.TtmlNode;
import androidx.media3.extractor.ts.PsExtractor;
import androidx.profileinstaller.ProfileVerifier;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.tv.material3.Border;
import androidx.tv.material3.ClickableSurfaceDefaults;
import androidx.tv.material3.SurfaceKt;
import com.badlogic.gdx.graphics.GL20;
import com.dh.myembyapp.data.DeviceIdManager;
import com.dh.myembyapp.data.ProxyConfig;
import com.dh.myembyapp.data.ProxySettings;
import com.dh.myembyapp.data.api.RetrofitClient;
import com.dh.myembyapp.data.model.AggregateResultSortMode;
import com.dh.myembyapp.data.model.BackupRouteConfig;
import com.dh.myembyapp.data.model.ImageTags;
import com.dh.myembyapp.data.model.ItemCounts;
import com.dh.myembyapp.data.model.Library;
import com.dh.myembyapp.data.model.MediaItem;
import com.dh.myembyapp.data.model.ServerConfig;
import com.dh.myembyapp.data.model.ServerPingState;
import com.dh.myembyapp.data.model.UserData;
import com.dh.myembyapp.data.preferences.ServerPreferences;
import com.dh.myembyapp.data.preferences.UserPreferences;
import com.dh.myembyapp.server.OnlineSubtitleConfigServerManager;
import com.dh.myembyapp.server.ProxyConfigServerManager;
import com.dh.myembyapp.server.SubtitleFontUploadServerManager;
import com.dh.myembyapp.server.WebDavSyncConfigServerManager;
import j$.util.Objects;
import java.net.Proxy;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KFunction;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;

/* JADX INFO: loaded from: D:\Ai\Github\ChaiSync-GH\build\decoded\classes4.dex */
public abstract class hr0 {
    public static final Integer A(State state) {
        return (Integer) state.getValue();
    }

    public static final String B(State state) {
        return (String) state.getValue();
    }

    public static final String C(State state) {
        return (String) state.getValue();
    }

    public static final boolean D(State state) {
        return ((Boolean) state.getValue()).booleanValue();
    }

    public static final uo0 E(State state) {
        return (uo0) state.getValue();
    }

    public static final boolean F(State state) {
        return ((Boolean) state.getValue()).booleanValue();
    }

    public static final Map G(State state) {
        return (Map) state.getValue();
    }

    public static final to0 H(MutableState mutableState) {
        return (to0) mutableState.getValue();
    }

    public static final boolean I(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    public static final boolean J(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    public static final boolean K(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    public static final String L(MutableState mutableState) {
        return (String) mutableState.getValue();
    }

    public static final ir0 M(MutableState mutableState) {
        return (ir0) mutableState.getValue();
    }

    public static final ProxyConfig N(State state) {
        return (ProxyConfig) state.getValue();
    }

    public static final void O(final float f, Composer composer, final int i) {
        int i2;
        Composer composer2;
        Composer composerStartRestartGroup = composer.startRestartGroup(-743980130);
        if ((i & 6) == 0) {
            i2 = (composerStartRestartGroup.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if (composerStartRestartGroup.shouldExecute((i2 & 3) != 2, i2 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-743980130, i2, -1, "com.dh.myembyapp.ui.screens.home.LibraryBatchLoadingSection (HomeScreen.kt:2234)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifier = PaddingKt.padding-qDBjuR0$default(SizeKt.fillMaxWidth$default(companion, 0.0f, 1, (Object) null), 0.0f, Dp.constructor-impl(10.0f), 0.0f, Dp.constructor-impl(4.0f), 5, (Object) null);
            Arrangement arrangement = Arrangement.INSTANCE;
            Arrangement.HorizontalOrVertical horizontalOrVertical = arrangement.spacedBy-0680j_4(Dp.constructor-impl(10.0f));
            Alignment.Companion companion2 = Alignment.Companion;
            MeasurePolicy measurePolicyColumnMeasurePolicy = ColumnKt.columnMeasurePolicy(horizontalOrVertical, companion2.getStart(), composerStartRestartGroup, 6);
            long currentCompositeKeyHashCode = ComposablesKt.getCurrentCompositeKeyHashCode(composerStartRestartGroup, 0);
            int i3 = (int) (currentCompositeKeyHashCode ^ (currentCompositeKeyHashCode >>> 32));
            CompositionLocalMap currentCompositionLocalMap = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifier);
            ComposeUiNode.Companion companion3 = ComposeUiNode.Companion;
            Function0 constructor = companion3.getConstructor();
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
            zr.z(companion3, composer3, measurePolicyColumnMeasurePolicy, composer3, currentCompositionLocalMap);
            Updater.set-impl(composer3, modifierMaterializeModifier, pd.h(companion3, composer3, Integer.valueOf(i3), composer3));
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier modifier2 = PaddingKt.padding-VpY3zN4$default(SizeKt.fillMaxWidth$default(companion, 0.0f, 1, (Object) null), f, 0.0f, 2, (Object) null);
            MeasurePolicy measurePolicyRowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement.getCenter(), companion2.getCenterVertically(), composerStartRestartGroup, 54);
            long currentCompositeKeyHashCode2 = ComposablesKt.getCurrentCompositeKeyHashCode(composerStartRestartGroup, 0);
            int i4 = (int) (currentCompositeKeyHashCode2 ^ (currentCompositeKeyHashCode2 >>> 32));
            CompositionLocalMap currentCompositionLocalMap2 = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifier2);
            Function0 constructor2 = companion3.getConstructor();
            if (composerStartRestartGroup.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composerStartRestartGroup.startReusableNode();
            if (composerStartRestartGroup.getInserting()) {
                composerStartRestartGroup.createNode(constructor2);
            } else {
                composerStartRestartGroup.useNode();
            }
            Composer composer4 = Updater.constructor-impl(composerStartRestartGroup);
            zr.z(companion3, composer4, measurePolicyRowMeasurePolicy, composer4, currentCompositionLocalMap2);
            Updater.set-impl(composer4, modifierMaterializeModifier2, pd.h(companion3, composer4, Integer.valueOf(i4), composer4));
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            ProgressIndicatorKt.CircularProgressIndicator-4lLiAd8(SizeKt.size-3ABfNKs(companion, Dp.constructor-impl(22.0f)), g81Var.b, Dp.constructor-impl(2.5f), 0L, 0, 0.0f, composerStartRestartGroup, 390, 56);
            SpacerKt.Spacer(SizeKt.width-3ABfNKs(companion, Dp.constructor-impl(12.0f)), composerStartRestartGroup, 6);
            MaterialTheme materialTheme = MaterialTheme.INSTANCE;
            int i5 = MaterialTheme.$stable;
            composer2 = composerStartRestartGroup;
            TextKt.Text-Nvy7gAk("正在加载", (Modifier) null, materialTheme.getColorScheme(composerStartRestartGroup, i5).getOnBackground-0d7_KjU(), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composerStartRestartGroup, i5).getTitleMedium(), composer2, 6, 0, 131066);
            if (y2.m(composer2)) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: np0
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    hr0.O(f, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public static final void P(Modifier modifier, FocusRequester focusRequester, Function0 function0, Function0 function1, Composer composer, int i) {
        Function0 function2;
        Modifier modifier2;
        Composer composerStartRestartGroup = composer.startRestartGroup(1913961023);
        int i2 = i | 6;
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changed(focusRequester) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            function2 = function0;
            i2 |= composerStartRestartGroup.changedInstance(function2) ? 256 : 128;
        } else {
            function2 = function0;
        }
        if ((i & 3072) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(function1) ? 2048 : 1024;
        }
        if (composerStartRestartGroup.shouldExecute((i2 & 1171) != 1170, i2 & 1)) {
            Modifier modifier3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1913961023, i2, -1, "com.dh.myembyapp.ui.screens.home.LibraryRowMoreButton (HomeScreen.kt:2803)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            uo uoVar = (uo) composerStartRestartGroup.consume(vo.a);
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            Shape shape = RoundedCornerShapeKt.RoundedCornerShape-0680j_4(Dp.constructor-impl(10.0f));
            Modifier modifierFocusRequester = FocusRequesterModifierKt.focusRequester(SizeKt.height-3ABfNKs(SizeKt.width-3ABfNKs(modifier3, uoVar.c(Dp.constructor-impl(36.0f))), uoVar.d), focusRequester);
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue2 == companion.getEmpty()) {
                objRememberedValue2 = new z70(21, mutableState);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifierFocusRequester, (Function1) objRememberedValue2);
            boolean z = (i2 & 7168) == 2048;
            Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (z || objRememberedValue3 == companion.getEmpty()) {
                objRememberedValue3 = new vq0(function1);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
            }
            Modifier modifierOnPreviewKeyEvent = KeyInputModifierKt.onPreviewKeyEvent(modifierOnFocusChanged, (Function1) objRememberedValue3);
            ClickableSurfaceDefaults clickableSurfaceDefaults = ClickableSurfaceDefaults.INSTANCE;
            MaterialTheme materialTheme = MaterialTheme.INSTANCE;
            int i3 = MaterialTheme.$stable;
            long j = Color.copy-wmQWz5c$default(materialTheme.getColorScheme(composerStartRestartGroup, i3).getSurfaceVariant-0d7_KjU(), 0.72f, 0.0f, 0.0f, 0.0f, 14, (Object) null);
            long j2 = g81Var.D;
            long j3 = materialTheme.getColorScheme(composerStartRestartGroup, i3).getOnSurface-0d7_KjU();
            long j4 = materialTheme.getColorScheme(composerStartRestartGroup, i3).getOnSurface-0d7_KjU();
            int i4 = ClickableSurfaceDefaults.$stable;
            int i5 = i4 << 15;
            Function0 function3 = function2;
            SurfaceKt.m632Surface05tvjtU(function3, modifierOnPreviewKeyEvent, null, false, 0.0f, clickableSurfaceDefaults.shape(shape, null, null, null, null, composerStartRestartGroup, i5, 30), clickableSurfaceDefaults.m401colorsoq7We08(j, j3, j2, j4, 0L, 0L, 0L, 0L, composerStartRestartGroup, i4 << 24, PsExtractor.VIDEO_STREAM_MASK), ClickableSurfaceDefaults.scale$default(clickableSurfaceDefaults, 0.0f, 1.03f, 0.0f, 0.0f, 0.0f, 29, null), clickableSurfaceDefaults.border(null, new Border(BorderStrokeKt.BorderStroke-cXLIe8U(Dp.constructor-impl(2.0f), g81Var.b), 0.0f, null, 6, null), null, null, null, composerStartRestartGroup, i5, 29), im0.c(g81Var), null, ComposableLambdaKt.rememberComposableLambda(-470860992, true, new q1(24, mutableState), composerStartRestartGroup, 54), composerStartRestartGroup, (i2 >> 6) & 14, 48, 1052);
            composerStartRestartGroup = composerStartRestartGroup;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier2 = modifier3;
        } else {
            composerStartRestartGroup.skipToGroupEnd();
            modifier2 = modifier;
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composerStartRestartGroup.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new ek0(modifier2, focusRequester, function0, function1, i, 1));
        }
    }

    public static final void Q(final String str, float f, final boolean z, final Function1 function1, Function0 function0, Composer composer, final int i) {
        int i2;
        boolean z2;
        float f2;
        Function0 function2;
        Composer composer2;
        MutableState mutableState;
        Object obj;
        Composer composerStartRestartGroup = composer.startRestartGroup(-772483875);
        if ((i & 6) == 0) {
            i2 = (composerStartRestartGroup.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changed(f) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            z2 = z;
            i2 |= composerStartRestartGroup.changed(z2) ? 256 : 128;
        } else {
            z2 = z;
        }
        if ((i & 3072) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(function1) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(function0) ? 16384 : 8192;
        }
        if (composerStartRestartGroup.shouldExecute((i2 & 9363) != 9362, i2 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-772483875, i2, -1, "com.dh.myembyapp.ui.screens.home.LibrarySkeletonSection (HomeScreen.kt:2876)");
            }
            int i3 = i2 & 14;
            boolean z3 = i3 == 4;
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            if (z3 || objRememberedValue == Composer.Companion.getEmpty()) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState2 = (MutableState) objRememberedValue;
            uo uoVar = (uo) composerStartRestartGroup.consume(vo.a);
            Boolean boolValueOf = Boolean.valueOf(z2);
            int i4 = 57344 & i2;
            boolean zChanged = ((i2 & 896) == 256) | composerStartRestartGroup.changed(mutableState2) | (i4 == 16384);
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (zChanged || objRememberedValue2 == Composer.Companion.getEmpty()) {
                mutableState = mutableState2;
                k6 k6Var = new k6(z2, function0, mutableState, null, 4);
                obj = null;
                composerStartRestartGroup.updateRememberedValue(k6Var);
                objRememberedValue2 = k6Var;
            } else {
                mutableState = mutableState2;
                obj = null;
            }
            EffectsKt.LaunchedEffect(boolValueOf, (Function2) objRememberedValue2, composerStartRestartGroup, (i2 >> 6) & 14);
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(companion, 0.0f, 1, obj);
            boolean z4 = (i3 == 4) | ((i2 & 7168) == 2048);
            Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (z4 || objRememberedValue3 == Composer.Companion.getEmpty()) {
                objRememberedValue3 = new gq0(0, str, function1);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
            }
            Modifier modifierFocusGroup = FocusableKt.focusGroup(FocusChangedModifierKt.onFocusChanged(modifierFillMaxWidth$default, (Function1) objRememberedValue3));
            Arrangement arrangement = Arrangement.INSTANCE;
            MeasurePolicy measurePolicyColumnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement.spacedBy-0680j_4(uoVar.a(Dp.constructor-impl(12.0f))), Alignment.Companion.getStart(), composerStartRestartGroup, 0);
            long currentCompositeKeyHashCode = ComposablesKt.getCurrentCompositeKeyHashCode(composerStartRestartGroup, 0);
            int i5 = (int) (currentCompositeKeyHashCode ^ (currentCompositeKeyHashCode >>> 32));
            CompositionLocalMap currentCompositionLocalMap = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifierFocusGroup);
            ComposeUiNode.Companion companion2 = ComposeUiNode.Companion;
            Function0 constructor = companion2.getConstructor();
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
            zr.z(companion2, composer3, measurePolicyColumnMeasurePolicy, composer3, currentCompositionLocalMap);
            Updater.set-impl(composer3, modifierMaterializeModifier, pd.h(companion2, composer3, Integer.valueOf(i5), composer3));
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            String strN = o71.n(str, " (加载中...)");
            MaterialTheme materialTheme = MaterialTheme.INSTANCE;
            int i6 = MaterialTheme.$stable;
            MutableState mutableState3 = mutableState;
            TextKt.Text-Nvy7gAk(strN, PaddingKt.padding-VpY3zN4$default(companion, f, 0.0f, 2, (Object) null), Color.copy-wmQWz5c$default(materialTheme.getColorScheme(composerStartRestartGroup, i6).getOnBackground-0d7_KjU(), 0.6f, 0.0f, 0.0f, 0.0f, 14, (Object) null), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composerStartRestartGroup, i6).getTitleLarge(), composerStartRestartGroup, 0, 0, 131064);
            Modifier modifierFillMaxWidth$default2 = SizeKt.fillMaxWidth$default(companion, 0.0f, 1, (Object) null);
            Arrangement.HorizontalOrVertical horizontalOrVertical = arrangement.spacedBy-0680j_4(Dp.constructor-impl(24.0f));
            f2 = f;
            PaddingValues paddingValues = PaddingKt.PaddingValues-YgX7TsA$default(f2, 0.0f, 2, (Object) null);
            boolean zChanged2 = composerStartRestartGroup.changed(uoVar) | composerStartRestartGroup.changed(mutableState3) | (i4 == 16384);
            Object objRememberedValue4 = composerStartRestartGroup.rememberedValue();
            if (zChanged2 || objRememberedValue4 == Composer.Companion.getEmpty()) {
                function2 = function0;
                objRememberedValue4 = new c1(uoVar, mutableState3, function2, 19);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue4);
            } else {
                function2 = function0;
            }
            LazyDslKt.LazyRow(modifierFillMaxWidth$default2, (LazyListState) null, paddingValues, false, horizontalOrVertical, (Alignment.Vertical) null, (FlingBehavior) null, false, (OverscrollEffect) null, (Function1) objRememberedValue4, composerStartRestartGroup, 24582, 490);
            composer2 = composerStartRestartGroup;
            composer2.endNode();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            f2 = f;
            function2 = function0;
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final Function0 function3 = function2;
            final float f3 = f2;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: xo0
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    hr0.Q(str, f3, z, function1, function3, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            });
        }
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r12v17 ??, new type: boolean
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException
        */
    public static final void R(java.lang.String r70, java.lang.String r71, boolean r72, java.util.List r73, boolean r74, boolean r75, java.lang.String r76, java.lang.String r77, boolean r78, kotlin.jvm.functions.Function2 r79, kotlin.jvm.functions.Function3 r80, kotlin.jvm.functions.Function2 r81, float r82, int r83, kotlin.jvm.functions.Function1 r84, kotlin.jvm.functions.Function0 r85, boolean r86, boolean r87, kotlin.jvm.functions.Function0 r88, androidx.compose.runtime.Composer r89, int r90, int r91) {
        /*
            Method dump skipped, instruction units count: 2353
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hr0.R(java.lang.String, java.lang.String, boolean, java.util.List, boolean, boolean, java.lang.String, java.lang.String, boolean, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function2, float, int, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0, boolean, boolean, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Failed to calculate best type for var: r10v4 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r10v4 ??, new type: boolean
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r3v5 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r3v5 ??, new type: boolean
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r6v27 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r6v27 ??, new type: boolean
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r12v8 ??, new type: boolean
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException
        */
    public static final void S(boolean r47, java.util.List r48, java.lang.String r49, java.lang.String r50, boolean r51, kotlin.jvm.functions.Function1 r52, kotlin.jvm.functions.Function2 r53, float r54, kotlin.jvm.functions.Function0 r55, androidx.compose.runtime.Composer r56, int r57) {
        /*
            Method dump skipped, instruction units count: 1140
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hr0.S(boolean, java.util.List, java.lang.String, java.lang.String, boolean, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function2, float, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void T(boolean z, final MediaItem mediaItem, int i, FocusRequester focusRequester, Function0 function0, Function0 function1, Function1 function2, Composer composer, int i2) {
        int i3;
        Composer composer2;
        SnapshotMutationPolicy snapshotMutationPolicy;
        int i4;
        String str;
        Object obj;
        Double playedPercentage;
        float fCoerceIn;
        float f;
        boolean z2;
        State state;
        Composer composerStartRestartGroup = composer.startRestartGroup(-574648603);
        if ((i2 & 6) == 0) {
            i3 = (composerStartRestartGroup.changed(z) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(mediaItem) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i3 |= composerStartRestartGroup.changed(i) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i3 |= composerStartRestartGroup.changed(focusRequester) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(function0) ? GL20.GL_COLOR_BUFFER_BIT : 8192;
        }
        if ((196608 & i2) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(function1) ? ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ : 65536;
        }
        if ((1572864 & i2) == 0) {
            i3 |= composerStartRestartGroup.changedInstance(function2) ? ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES : 524288;
        }
        if (composerStartRestartGroup.shouldExecute((599187 & i3) != 599186, i3 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-574648603, i3, -1, "com.dh.myembyapp.ui.screens.home.ResumeEpisodeCard (HomeScreen.kt:3278)");
            }
            final Context context = (Context) composerStartRestartGroup.consume(AndroidCompositionLocals_androidKt.getLocalContext());
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            final g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue == companion.getEmpty()) {
                objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerStartRestartGroup);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
            }
            CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue2 == companion.getEmpty()) {
                objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            final MutableState mutableState = (MutableState) objRememberedValue2;
            Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue3 == companion.getEmpty()) {
                MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default);
                objRememberedValue3 = mutableStateMutableStateOf$default;
            }
            MutableState mutableState2 = (MutableState) objRememberedValue3;
            Object objRememberedValue4 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue4 == companion.getEmpty()) {
                MutableState mutableStateMutableStateOf$default2 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default2);
                objRememberedValue4 = mutableStateMutableStateOf$default2;
                snapshotMutationPolicy = null;
            } else {
                snapshotMutationPolicy = null;
            }
            MutableState mutableState3 = (MutableState) objRememberedValue4;
            Object objRememberedValue5 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue5 == companion.getEmpty()) {
                objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy, snapshotMutationPolicy, 2, snapshotMutationPolicy);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue5);
            }
            MutableState mutableState4 = (MutableState) objRememberedValue5;
            Object objRememberedValue6 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue6 == companion.getEmpty()) {
                objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy, 2, snapshotMutationPolicy);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
            }
            State state2 = (MutableState) objRememberedValue6;
            Boolean boolValueOf = Boolean.valueOf(z);
            int i5 = i3 & 14;
            boolean z3 = i5 == 4;
            Object objRememberedValue7 = composerStartRestartGroup.rememberedValue();
            if (z3 || objRememberedValue7 == companion.getEmpty()) {
                i4 = 1;
                objRememberedValue7 = new pq0(z, mutableState, null, i4);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue7);
            } else {
                i4 = 1;
            }
            EffectsKt.LaunchedEffect(boolValueOf, (Function2) objRememberedValue7, composerStartRestartGroup, i5);
            boolean z4 = (z && U(mutableState)) ? i4 : 0;
            Object objRememberedValue8 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue8 == companion.getEmpty()) {
                objRememberedValue8 = new x50(19, mutableState);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
            }
            BackHandlerKt.BackHandler(z4, (Function0) objRememberedValue8, composerStartRestartGroup, 48, 0);
            int i6 = (composerStartRestartGroup.changed(mediaItem.getId()) ? 1 : 0) | (composerStartRestartGroup.changed(mediaItem.getImageTags()) ? 1 : 0) | (composerStartRestartGroup.changed(mediaItem.getBackdropImageTags()) ? 1 : 0) | (composerStartRestartGroup.changed(mediaItem.getSeriesId()) ? 1 : 0) | ((i3 & 896) == 256 ? i4 : 0);
            Object objRememberedValue9 = composerStartRestartGroup.rememberedValue();
            if (i6 != 0 || objRememberedValue9 == companion.getEmpty()) {
                if (mediaItem.getSeriesName() == null) {
                    mediaItem.getName();
                }
                ImageTags imageTags = mediaItem.getImageTags();
                String primary = imageTags != null ? imageTags.getPrimary() : null;
                ImageTags imageTags2 = mediaItem.getImageTags();
                String thumb = imageTags2 != null ? imageTags2.getThumb() : null;
                List<String> backdropImageTags = mediaItem.getBackdropImageTags();
                String str2 = backdropImageTags != null ? (String) CollectionsKt.firstOrNull((List) backdropImageTags) : null;
                String seriesId = mediaItem.getSeriesId();
                ArrayList arrayList = new ArrayList();
                if (seriesId != null) {
                    str = null;
                    arrayList.add(RetrofitClient.INSTANCE.getImageUrl(seriesId, "Thumb", null, Integer.valueOf(i)));
                } else {
                    str = null;
                }
                if (seriesId != null) {
                    arrayList.add(RetrofitClient.INSTANCE.getImageUrl(seriesId, "Backdrop", str, Integer.valueOf(i)));
                }
                if (seriesId != null) {
                    arrayList.add(RetrofitClient.INSTANCE.getImageUrl(seriesId, "Primary", str, Integer.valueOf(i)));
                }
                if (thumb != null) {
                    arrayList.add(RetrofitClient.INSTANCE.getImageUrl(mediaItem.getId(), "Thumb", thumb, Integer.valueOf(i)));
                }
                if (str2 != null) {
                    arrayList.add(RetrofitClient.INSTANCE.getImageUrl(mediaItem.getId(), "Backdrop", str2, Integer.valueOf(i)));
                }
                if (primary != null) {
                    arrayList.add(RetrofitClient.INSTANCE.getImageUrl(mediaItem.getId(), "Primary", primary, Integer.valueOf(i)));
                }
                ArrayList arrayList2 = new ArrayList();
                int size = arrayList.size();
                int i7 = 0;
                while (i7 < size) {
                    Object obj2 = arrayList.get(i7);
                    i7++;
                    if (((String) obj2).length() > 0) {
                        arrayList2.add(obj2);
                    }
                }
                composerStartRestartGroup.updateRememberedValue(arrayList2);
                obj = arrayList2;
            } else {
                state2 = state2;
                coroutineScope = coroutineScope;
                obj = objRememberedValue9;
            }
            List list = (List) obj;
            Object objRememberedValue10 = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (objRememberedValue10 == companion2.getEmpty()) {
                playedPercentage = null;
                objRememberedValue10 = zr.l(0, (SnapshotMutationPolicy) null, 2, (Object) null, composerStartRestartGroup);
            } else {
                playedPercentage = null;
            }
            MutableState mutableState5 = (MutableState) objRememberedValue10;
            String str3 = (list.isEmpty() || V(mutableState5) >= list.size()) ? TtmlNode.ANONYMOUS_REGION_ID : (String) list.get(((Number) mutableState5.getValue()).intValue());
            UserData userData = mediaItem.getUserData();
            Object objValueOf = userData != null ? Long.valueOf(userData.getPlaybackPositionTicks()) : playedPercentage;
            UserData userData2 = mediaItem.getUserData();
            boolean zChanged = composerStartRestartGroup.changed(userData2 != null ? userData2.getPlayedPercentage() : playedPercentage) | composerStartRestartGroup.changed(objValueOf) | composerStartRestartGroup.changed(mediaItem.getRunTimeTicks());
            Object objRememberedValue11 = composerStartRestartGroup.rememberedValue();
            if (zChanged || objRememberedValue11 == companion2.getEmpty()) {
                UserData userData3 = mediaItem.getUserData();
                if (userData3 != null) {
                    playedPercentage = userData3.getPlayedPercentage();
                }
                if (playedPercentage == null || playedPercentage.doubleValue() <= 0.0d) {
                    UserData userData4 = mediaItem.getUserData();
                    long playbackPositionTicks = userData4 != null ? userData4.getPlaybackPositionTicks() : 0L;
                    Long runTimeTicks = mediaItem.getRunTimeTicks();
                    long jLongValue = runTimeTicks != null ? runTimeTicks.longValue() : 0L;
                    fCoerceIn = (playbackPositionTicks <= 0 || jLongValue <= 0) ? 0.0f : RangesKt.coerceIn(playbackPositionTicks / jLongValue, 0.0f, 1.0f);
                } else {
                    fCoerceIn = RangesKt.coerceIn((float) (playedPercentage.doubleValue() / 100.0d), 0.0f, 1.0f);
                }
                objRememberedValue11 = Float.valueOf(fCoerceIn);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue11);
            }
            final float fFloatValue = ((Number) objRememberedValue11).floatValue();
            boolean zChanged2 = composerStartRestartGroup.changed(mediaItem.getParentIndexNumber()) | composerStartRestartGroup.changed(mediaItem.getIndexNumber()) | composerStartRestartGroup.changed(mediaItem.getName());
            Object objRememberedValue12 = composerStartRestartGroup.rememberedValue();
            if (zChanged2 || objRememberedValue12 == companion2.getEmpty()) {
                StringBuilder sb = new StringBuilder();
                if (mediaItem.getParentIndexNumber() != null) {
                    sb.append("S" + StringsKt.E(2, String.valueOf(mediaItem.getParentIndexNumber().intValue())));
                }
                if (mediaItem.getIndexNumber() != null) {
                    if (sb.length() > 0) {
                        sb.append(" - ");
                    }
                    sb.append("E" + StringsKt.E(2, String.valueOf(mediaItem.getIndexNumber().intValue())));
                }
                if (mediaItem.getName().length() > 0) {
                    if (sb.length() > 0) {
                        sb.append(" - ");
                    }
                    sb.append(mediaItem.getName());
                }
                objRememberedValue12 = sb.toString();
                composerStartRestartGroup.updateRememberedValue(objRememberedValue12);
            }
            final String str4 = (String) objRememberedValue12;
            uo uoVar = (uo) composerStartRestartGroup.consume(vo.a);
            float f2 = uoVar.z;
            float f3 = uoVar.B;
            final float f4 = uoVar.A;
            float f5 = Dp.constructor-impl(14.0f);
            final RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.RoundedCornerShape-0680j_4(f5);
            composerStartRestartGroup.startReplaceGroup(1813636216);
            if (((Boolean) state2.getValue()).booleanValue()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(2025277826, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-detachedFocusBorderEnabled> (Theme.kt:152)");
                }
                boolean zBooleanValue = ((Boolean) composerStartRestartGroup.consume(k92.h)).booleanValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                f = zBooleanValue ? Dp.constructor-impl(5.0f) : Dp.constructor-impl(0.0f);
            } else {
                f = Dp.constructor-impl(0.0f);
                list = list;
                mutableState5 = mutableState5;
            }
            final float f6 = f;
            composerStartRestartGroup.endReplaceGroup();
            final RoundedCornerShape roundedCornerShape2 = RoundedCornerShapeKt.RoundedCornerShape-0680j_4(Dp.constructor-impl(f5 + f6));
            Color.copy-wmQWz5c$default(MaterialTheme.INSTANCE.getColorScheme(composerStartRestartGroup, MaterialTheme.$stable).getOnSurface-0d7_KjU(), 0.66f, 0.0f, 0.0f, 0.0f, 14, (Object) null);
            float f7 = ((Boolean) state2.getValue()).booleanValue() ? 1.03f : 1.0f;
            boolean zChanged3 = composerStartRestartGroup.changed(f2) | ((i3 & 7168) == 2048) | composerStartRestartGroup.changed(f3);
            Object objRememberedValue13 = composerStartRestartGroup.rememberedValue();
            if (zChanged3 || objRememberedValue13 == companion2.getEmpty()) {
                Modifier modifierFocusRequester = SizeKt.height-3ABfNKs(SizeKt.width-3ABfNKs(Modifier.Companion, f2), f3);
                if (focusRequester != null) {
                    modifierFocusRequester = FocusRequesterModifierKt.focusRequester(modifierFocusRequester, focusRequester);
                }
                objRememberedValue13 = modifierFocusRequester;
                composerStartRestartGroup.updateRememberedValue(objRememberedValue13);
            }
            Modifier modifier = (Modifier) objRememberedValue13;
            boolean z5 = ((458752 & i3) == 131072) | ((57344 & i3) == 16384);
            Object objRememberedValue14 = composerStartRestartGroup.rememberedValue();
            if (z5 || objRememberedValue14 == companion2.getEmpty()) {
                z2 = false;
                objRememberedValue14 = new wp0(function1, function0, mutableState, 0);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue14);
            } else {
                z2 = false;
            }
            Function0 function3 = (Function0) objRememberedValue14;
            if ((i3 & 3670016) == 1048576) {
                z2 = true;
            }
            Object objRememberedValue15 = composerStartRestartGroup.rememberedValue();
            if (z2 || objRememberedValue15 == companion2.getEmpty()) {
                state = state2;
                objRememberedValue15 = new y7(function2, state, 8);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue15);
            } else {
                state = state2;
            }
            Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifier, (Function1) objRememberedValue15);
            CoroutineScope coroutineScope2 = coroutineScope;
            boolean zChangedInstance = composerStartRestartGroup.changedInstance(coroutineScope2);
            Object objRememberedValue16 = composerStartRestartGroup.rememberedValue();
            if (zChangedInstance || objRememberedValue16 == companion2.getEmpty()) {
                objRememberedValue16 = new cr0(coroutineScope2, mutableState, mutableState2, mutableState3, mutableState4);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue16);
            }
            Modifier modifierOnPreviewKeyEvent = KeyInputModifierKt.onPreviewKeyEvent(modifierOnFocusChanged, (Function1) objRememberedValue16);
            boolean zChanged4 = composerStartRestartGroup.changed(f7);
            Object objRememberedValue17 = composerStartRestartGroup.rememberedValue();
            if (zChanged4 || objRememberedValue17 == companion2.getEmpty()) {
                objRememberedValue17 = new p5(f7, 2);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue17);
            }
            Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierOnPreviewKeyEvent, (Function1) objRememberedValue17);
            ClickableSurfaceDefaults clickableSurfaceDefaults = ClickableSurfaceDefaults.INSTANCE;
            Color.Companion companion3 = Color.Companion;
            final State state3 = state;
            final String str5 = str3;
            final List list2 = list;
            final MutableState mutableState6 = mutableState5;
            SurfaceKt.m632Surface05tvjtU(function3, modifierGraphicsLayer, null, false, 0.0f, null, clickableSurfaceDefaults.m401colorsoq7We08(companion3.getTransparent-0d7_KjU(), 0L, companion3.getTransparent-0d7_KjU(), 0L, 0L, 0L, 0L, 0L, composerStartRestartGroup, (ClickableSurfaceDefaults.$stable << 24) | 390, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION), null, null, null, null, ComposableLambdaKt.rememberComposableLambda(-18315930, true, new Function3() { // from class: xp0
                /* JADX WARN: Failed to calculate best type for var: r1v38 ??
                jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v38 ??, new type: int
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
                Caused by: java.lang.NullPointerException
                 */
                /* JADX WARN: Failed to calculate best type for var: r1v8 ??
                jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v8 ??, new type: int
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                Caused by: java.lang.NullPointerException
                 */
                /* JADX WARN: Failed to calculate best type for var: r1v8 ??
                jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v8 ??, new type: int
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
                Caused by: java.lang.NullPointerException
                 */
                /* JADX WARN: Failed to calculate best type for var: r1v9 ??
                jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v9 ??, new type: int
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
                Caused by: java.lang.NullPointerException
                 */
                /*  JADX ERROR: Types fix failed
                    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r1v8 ??, new type: char
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                    Caused by: java.lang.NullPointerException
                    */
                public final java.lang.Object invoke(java.lang.Object r65, java.lang.Object r66, java.lang.Object r67) {
                    /*
                        Method dump skipped, instruction units count: 2099
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.xp0.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerStartRestartGroup, 54), composerStartRestartGroup, 0, 48, 1980);
            composer2 = composerStartRestartGroup;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new s4(z, mediaItem, i, focusRequester, function0, function1, function2, i2));
        }
    }

    public static final boolean U(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    public static final int V(MutableState mutableState) {
        return ((Number) mutableState.getValue()).intValue();
    }

    public static final void W(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    /* JADX WARN: Code duplicated, block: B:168:0x02ec  */
    /* JADX WARN: Code duplicated, block: B:171:0x0329  */
    /* JADX WARN: Code duplicated, block: B:172:0x032c  */
    /* JADX WARN: Code duplicated, block: B:175:0x0334  */
    /* JADX WARN: Code duplicated, block: B:176:0x0337  */
    /* JADX WARN: Code duplicated, block: B:179:0x0340  */
    /* JADX WARN: Code duplicated, block: B:180:0x0343  */
    /* JADX WARN: Code duplicated, block: B:183:0x034d  */
    /* JADX WARN: Code duplicated, block: B:184:0x0350  */
    /* JADX WARN: Code duplicated, block: B:187:0x035a  */
    /* JADX WARN: Code duplicated, block: B:188:0x035d  */
    /* JADX WARN: Code duplicated, block: B:193:0x036b  */
    /* JADX WARN: Code duplicated, block: B:198:0x03d0  */
    /* JADX WARN: Code duplicated, block: B:199:0x03d3  */
    /* JADX WARN: Code duplicated, block: B:201:0x03d6  */
    /* JADX WARN: Code duplicated, block: B:202:0x03d9  */
    /* JADX WARN: Code duplicated, block: B:205:0x03de  */
    /* JADX WARN: Code duplicated, block: B:206:0x03e1  */
    /* JADX WARN: Code duplicated, block: B:209:0x03e7  */
    /* JADX WARN: Code duplicated, block: B:210:0x03ea  */
    /* JADX WARN: Code duplicated, block: B:213:0x03f0  */
    /* JADX WARN: Code duplicated, block: B:214:0x03f3  */
    /* JADX WARN: Code duplicated, block: B:217:0x03fd  */
    /* JADX WARN: Code duplicated, block: B:218:0x0400  */
    /* JADX WARN: Code duplicated, block: B:221:0x040b  */
    /* JADX WARN: Code duplicated, block: B:222:0x040e  */
    /* JADX WARN: Code duplicated, block: B:225:0x041f  */
    /* JADX WARN: Code duplicated, block: B:226:0x0422  */
    /* JADX WARN: Code duplicated, block: B:229:0x042f  */
    /* JADX WARN: Code duplicated, block: B:230:0x0432  */
    /* JADX WARN: Code duplicated, block: B:237:0x045b  */
    /* JADX WARN: Code duplicated, block: B:242:0x04b8  */
    /* JADX WARN: Code duplicated, block: B:245:0x04d0  */
    /* JADX WARN: Code duplicated, block: B:248:0x04f4  */
    /* JADX WARN: Code duplicated, block: B:253:0x0527  */
    /* JADX WARN: Code duplicated, block: B:255:0x052c  */
    /* JADX WARN: Code duplicated, block: B:257:0x0530  */
    /* JADX WARN: Code duplicated, block: B:258:0x0532  */
    /* JADX WARN: Code duplicated, block: B:265:0x0546  */
    /* JADX WARN: Code duplicated, block: B:269:0x0598  */
    /* JADX WARN: Code duplicated, block: B:272:0x05a4  */
    /* JADX WARN: Code duplicated, block: B:273:0x05a8  */
    /* JADX WARN: Code duplicated, block: B:276:0x0654  */
    /* JADX WARN: Code duplicated, block: B:277:0x0656  */
    /* JADX WARN: Code duplicated, block: B:280:0x0661  */
    /* JADX WARN: Code duplicated, block: B:281:0x0663  */
    /* JADX WARN: Code duplicated, block: B:284:0x066b  */
    /* JADX WARN: Code duplicated, block: B:285:0x066d  */
    /* JADX WARN: Code duplicated, block: B:288:0x0674  */
    /* JADX WARN: Code duplicated, block: B:289:0x0676  */
    /* JADX WARN: Code duplicated, block: B:292:0x068d  */
    /* JADX WARN: Code duplicated, block: B:293:0x068f  */
    /* JADX WARN: Code duplicated, block: B:300:0x06a4  */
    /* JADX WARN: Code duplicated, block: B:303:0x06e9  */
    /* JADX WARN: Code duplicated, block: B:311:0x0508 A[SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:248:0x04f4, please report this as an issue */
    public static final void X(final boolean z, final List list, final String str, final Integer num, final String str2, final String str3, final Function1 function1, final boolean z2, final boolean z3, final Function3 function3, final Function1 function2, final float f, final int i, final Function0 function0, Composer composer, final int i2, final int i3) {
        int i4;
        int i5;
        Composer composer2;
        int i6;
        MutableState mutableState;
        State state;
        State state2;
        MutableState mutableState2;
        char c;
        int i7;
        List list2;
        Object objJoinToString$default;
        boolean zChangedInstance;
        Object objRememberedValue;
        LazyListState lazyListState;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        Object dr0Var;
        int i21;
        int i22;
        MutableState mutableState3;
        int i23;
        MutableState mutableState4;
        int i24;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        int i30;
        int i31;
        LazyListState lazyListState2;
        int i32;
        int i33;
        int i34;
        Object objRememberedValue2;
        List list3;
        LazyListState lazyListState3;
        MutableState mutableState5;
        MutableState mutableState6;
        MutableState mutableState7;
        MutableState mutableState8;
        MutableState mutableState9;
        Composer composer3;
        boolean zChanged;
        Object objRememberedValue3;
        LinkedHashMap linkedHashMap;
        ArrayList arrayList;
        Iterator it;
        String id;
        int iIntValue;
        Object obj;
        final List list4;
        boolean z4;
        boolean z5;
        boolean z6;
        Object objRememberedValue4;
        Function0 constructor;
        final LazyListState lazyListState4;
        Composer composer4;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        Object objRememberedValue5;
        Composer composerStartRestartGroup = composer.startRestartGroup(875611050);
        if ((i2 & 6) == 0) {
            i4 = (composerStartRestartGroup.changed("继续观看") ? 4 : 2) | i2;
        } else {
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            i4 |= composerStartRestartGroup.changed(z) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i4 |= composerStartRestartGroup.changedInstance(list) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i4 |= composerStartRestartGroup.changed(str) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i4 |= composerStartRestartGroup.changed(num) ? GL20.GL_COLOR_BUFFER_BIT : 8192;
        }
        if ((196608 & i2) == 0) {
            i4 |= composerStartRestartGroup.changed(str2) ? ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ : 65536;
        }
        if ((1572864 & i2) == 0) {
            i4 |= composerStartRestartGroup.changed(str3) ? ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES : 524288;
        }
        if ((12582912 & i2) == 0) {
            i4 |= composerStartRestartGroup.changedInstance(function1) ? 8388608 : 4194304;
        }
        if ((100663296 & i2) == 0) {
            i4 |= composerStartRestartGroup.changed(z2) ? 67108864 : 33554432;
        }
        if ((i2 & 805306368) == 0) {
            i4 |= composerStartRestartGroup.changed(z3) ? 536870912 : 268435456;
        }
        if ((i3 & 6) == 0) {
            i5 = i3 | (composerStartRestartGroup.changedInstance(function3) ? 4 : 2);
        } else {
            i5 = i3;
        }
        if ((i3 & 48) == 0) {
            i5 |= composerStartRestartGroup.changedInstance(function2) ? 32 : 16;
        }
        if ((i3 & 384) == 0) {
            i5 |= composerStartRestartGroup.changed(f) ? 256 : 128;
        }
        if ((i3 & 3072) == 0) {
            i5 |= composerStartRestartGroup.changed(i) ? 2048 : 1024;
        }
        if ((i3 & 24576) == 0) {
            i5 |= composerStartRestartGroup.changedInstance(function0) ? GL20.GL_COLOR_BUFFER_BIT : 8192;
        }
        int i35 = i5;
        if (composerStartRestartGroup.shouldExecute(((i4 & 306783379) == 306783378 && (i35 & 9363) == 9362) ? false : true, i4 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(875611050, i4, i35, "com.dh.myembyapp.ui.screens.home.ResumeItemSection (HomeScreen.kt:2996)");
            }
            LazyListState lazyListStateA0 = a0(composerStartRestartGroup, "resume_section_继续观看");
            uo uoVar = (uo) composerStartRestartGroup.consume(vo.a);
            Object objRememberedValue6 = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (objRememberedValue6 == companion.getEmpty()) {
                objRememberedValue6 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerStartRestartGroup);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue6;
            Object objRememberedValue7 = composerStartRestartGroup.rememberedValue();
            int i36 = i4;
            if (objRememberedValue7 == companion.getEmpty()) {
                objRememberedValue7 = zr.n(composerStartRestartGroup);
            }
            final FocusRequester focusRequester = (FocusRequester) objRememberedValue7;
            Object objRememberedValue8 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue8 == companion.getEmpty()) {
                i6 = 2;
                objRememberedValue8 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
            } else {
                i6 = 2;
            }
            MutableState mutableState10 = (MutableState) objRememberedValue8;
            Object objRememberedValue9 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue9 == companion.getEmpty()) {
                objRememberedValue9 = SnapshotStateKt.mutableStateOf$default((Object) null, (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
            }
            MutableState mutableState11 = (MutableState) objRememberedValue9;
            Object objRememberedValue10 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue10 == companion.getEmpty()) {
                objRememberedValue10 = SnapshotStateKt.mutableStateOf$default((Object) null, (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue10);
            }
            MutableState mutableState12 = (MutableState) objRememberedValue10;
            Object objRememberedValue11 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue11 == companion.getEmpty()) {
                objRememberedValue11 = SnapshotStateKt.mutableStateOf$default((Object) null, (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue11);
            }
            MutableState mutableState13 = (MutableState) objRememberedValue11;
            Object objRememberedValue12 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue12 == companion.getEmpty()) {
                objRememberedValue12 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue12);
            }
            MutableState mutableState14 = (MutableState) objRememberedValue12;
            Object objRememberedValue13 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue13 == companion.getEmpty()) {
                objRememberedValue13 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue13);
            }
            MutableState mutableState15 = (MutableState) objRememberedValue13;
            Object objRememberedValue14 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue14 == companion.getEmpty()) {
                objRememberedValue14 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue14);
            }
            MutableState mutableState16 = (MutableState) objRememberedValue14;
            Object objRememberedValue15 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue15 == companion.getEmpty()) {
                objRememberedValue15 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue15);
            }
            MutableState mutableState17 = (MutableState) objRememberedValue15;
            State stateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(Boolean.valueOf(z2), composerStartRestartGroup, (i36 >> 24) & 14);
            Boolean bool = (Boolean) mutableState17.getValue();
            bool.getClass();
            State stateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(bool, composerStartRestartGroup, 0);
            boolean zChanged2 = composerStartRestartGroup.changed(list);
            Object objRememberedValue16 = composerStartRestartGroup.rememberedValue();
            if (zChanged2) {
                mutableState = mutableState17;
            } else {
                mutableState = mutableState17;
                if (objRememberedValue16 != companion.getEmpty()) {
                    list2 = list;
                    state = stateRememberUpdatedState2;
                    state2 = stateRememberUpdatedState;
                    objJoinToString$default = objRememberedValue16;
                    mutableState2 = mutableState;
                    c = 2;
                    i7 = 4;
                }
                String str4 = (String) objJoinToString$default;
                zChangedInstance = composerStartRestartGroup.changedInstance(list2) | composerStartRestartGroup.changed(state2) | composerStartRestartGroup.changed(state) | composerStartRestartGroup.changed(r29);
                objRememberedValue = composerStartRestartGroup.rememberedValue();
                if (!zChangedInstance || objRememberedValue == companion.getEmpty()) {
                    lazyListState = r29;
                    State state3 = state;
                    i8 = 0;
                    i9 = 1;
                    g7 g7Var = new g7(list, lazyListState, state2, state3, (Continuation) null, 8);
                    composerStartRestartGroup.updateRememberedValue(g7Var);
                    objRememberedValue = g7Var;
                } else {
                    lazyListState = lazyListStateA0;
                    i8 = 0;
                    i9 = 1;
                }
                EffectsKt.LaunchedEffect(str4, (Function2) objRememberedValue, composerStartRestartGroup, i8);
                Boolean boolValueOf = Boolean.valueOf(z);
                Boolean boolValueOf2 = Boolean.valueOf(z2);
                Object[] objArr = new Object[6];
                objArr[i8] = boolValueOf;
                objArr[i9] = str;
                objArr[c] = num;
                objArr[3] = str2;
                objArr[i7] = str3;
                objArr[5] = boolValueOf2;
                i10 = i36 & 112;
                if (i10 == 32) {
                    i11 = i9;
                } else {
                    i11 = i8;
                }
                i12 = 234881024 & i36;
                if (i12 == 67108864) {
                    i13 = i9;
                } else {
                    i13 = i8;
                }
                int i37 = i13 | i11;
                i14 = 3670016 & i36;
                if (i14 == 1048576) {
                    i15 = i9;
                } else {
                    i15 = i8;
                }
                int i38 = i37 | i15;
                i16 = i36 & 14;
                if (i16 == i7) {
                    i17 = i9;
                } else {
                    i17 = 0;
                }
                int i39 = i17 | i38;
                i18 = i36 & 7168;
                if (i18 == 2048) {
                    i19 = i9;
                } else {
                    i19 = 0;
                }
                i20 = i39 | i19;
                Object objRememberedValue17 = composerStartRestartGroup.rememberedValue();
                if (i20 == 0 || objRememberedValue17 == companion.getEmpty()) {
                    i21 = i10;
                    i22 = i36;
                    mutableState3 = mutableState15;
                    i23 = ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
                    mutableState4 = mutableState14;
                    dr0Var = new dr0(z, z2, str3, str, mutableState4, mutableState3, null);
                    composerStartRestartGroup.updateRememberedValue(dr0Var);
                } else {
                    i21 = i10;
                    i22 = i36;
                    dr0Var = objRememberedValue17;
                    mutableState3 = mutableState15;
                    i23 = ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
                    mutableState4 = mutableState14;
                }
                EffectsKt.LaunchedEffect(objArr, (Function2) dr0Var, composerStartRestartGroup, 0);
                Boolean boolValueOf3 = Boolean.valueOf(z);
                Boolean boolValueOf4 = Boolean.valueOf(z2);
                Boolean boolValueOf5 = Boolean.valueOf(z3);
                Object[] objArr2 = new Object[8];
                objArr2[0] = boolValueOf3;
                objArr2[i9] = boolValueOf4;
                objArr2[c] = boolValueOf5;
                objArr2[3] = list;
                objArr2[4] = str;
                objArr2[5] = num;
                objArr2[6] = str2;
                objArr2[7] = str3;
                if (i21 == 32) {
                    i24 = i9;
                } else {
                    i24 = 0;
                }
                if (i14 == i23) {
                    i25 = i9;
                } else {
                    i25 = 0;
                }
                int i40 = i24 | i25;
                if (i16 == 4) {
                    i26 = i9;
                } else {
                    i26 = 0;
                }
                int i41 = i40 | i26;
                if (i18 == 2048) {
                    i27 = i9;
                } else {
                    i27 = 0;
                }
                int i42 = i41 | i27;
                if (i12 == 67108864) {
                    i28 = i9;
                } else {
                    i28 = 0;
                }
                int i43 = i42 | i28;
                if ((i22 & 1879048192) == 536870912) {
                    i29 = i9;
                } else {
                    i29 = 0;
                }
                int i44 = i43 | i29;
                if ((i22 & 57344) == 16384) {
                    i30 = i9;
                } else {
                    i30 = 0;
                }
                int i45 = i44 | i30 | (composerStartRestartGroup.changedInstance(list) ? 1 : 0);
                if ((i22 & 458752) == 131072) {
                    i31 = i9;
                } else {
                    i31 = 0;
                }
                int i46 = i45 | i31;
                lazyListState2 = lazyListState;
                int i47 = i46 | (composerStartRestartGroup.changed(lazyListState2) ? 1 : 0);
                i32 = i35 & 57344;
                if (i32 == 16384) {
                    i33 = i9;
                } else {
                    i33 = 0;
                }
                i34 = i47 | i33;
                objRememberedValue2 = composerStartRestartGroup.rememberedValue();
                if (i34 == 0 || objRememberedValue2 == companion.getEmpty()) {
                    fr0 fr0Var = new fr0(z, str3, str, z2, z3, num, list, str2, lazyListState2, function0, mutableState4, mutableState3, mutableState12, mutableState13, mutableState16, mutableState10, mutableState11, null);
                    list3 = list;
                    lazyListState3 = lazyListState2;
                    mutableState5 = mutableState12;
                    mutableState6 = mutableState13;
                    mutableState7 = mutableState16;
                    mutableState8 = mutableState10;
                    mutableState9 = mutableState11;
                    composer3 = composerStartRestartGroup;
                    composer3.updateRememberedValue(fr0Var);
                    objRememberedValue2 = fr0Var;
                } else {
                    list3 = list;
                    lazyListState3 = lazyListState2;
                    composer3 = composerStartRestartGroup;
                    mutableState8 = mutableState10;
                    mutableState9 = mutableState11;
                    mutableState5 = mutableState12;
                    mutableState6 = mutableState13;
                    mutableState7 = mutableState16;
                }
                EffectsKt.LaunchedEffect(objArr2, (Function2) objRememberedValue2, composer3, 0);
                zChanged = composer3.changed(list3);
                objRememberedValue3 = composer3.rememberedValue();
                if (zChanged || objRememberedValue3 == companion.getEmpty()) {
                    obj = objRememberedValue3;
                    linkedHashMap = new LinkedHashMap();
                    arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                    it = list3.iterator();
                    while (it.hasNext()) {
                        id = ((MediaItem) it.next()).getId();
                        iIntValue = ((Number) j$.util.Map.-EL.getOrDefault(linkedHashMap, id, 0)).intValue();
                        linkedHashMap.put(id, Integer.valueOf(iIntValue + 1));
                        if (iIntValue != 0) {
                            id = id + "_dup" + iIntValue;
                        }
                        arrayList.add(id);
                    }
                    composer3.updateRememberedValue(arrayList);
                    obj = arrayList;
                }
                obj = objRememberedValue3;
                list4 = (List) obj;
                Modifier.Companion companion2 = Modifier.Companion;
                Modifier modifierFocusGroup = FocusableKt.focusGroup(SizeKt.fillMaxWidth$default(companion2, 0.0f, 1, (Object) null));
                if ((i22 & 29360128) == 8388608) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (i16 == 4) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                z6 = z4 | z5;
                objRememberedValue4 = composer3.rememberedValue();
                if (z6 || objRememberedValue4 == Composer.Companion.getEmpty()) {
                    objRememberedValue4 = new y7(function1, mutableState2, 9);
                    composer3.updateRememberedValue(objRememberedValue4);
                }
                Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifierFocusGroup, (Function1) objRememberedValue4);
                Arrangement arrangement = Arrangement.INSTANCE;
                MeasurePolicy measurePolicyColumnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement.spacedBy-0680j_4(uoVar.a(Dp.constructor-impl(11.0f))), Alignment.Companion.getStart(), composer3, 0);
                long currentCompositeKeyHashCode = ComposablesKt.getCurrentCompositeKeyHashCode(composer3, 0);
                int i48 = (int) (currentCompositeKeyHashCode ^ (currentCompositeKeyHashCode >>> 32));
                CompositionLocalMap currentCompositionLocalMap = composer3.getCurrentCompositionLocalMap();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierOnFocusChanged);
                ComposeUiNode.Companion companion3 = ComposeUiNode.Companion;
                constructor = companion3.getConstructor();
                if (composer3.getApplier() == null) {
                    ComposablesKt.invalidApplier();
                }
                composer3.startReusableNode();
                if (composer3.getInserting()) {
                    composer3.createNode(constructor);
                } else {
                    composer3.useNode();
                }
                Composer composer5 = Updater.constructor-impl(composer3);
                zr.z(companion3, composer5, measurePolicyColumnMeasurePolicy, composer5, currentCompositionLocalMap);
                Updater.set-impl(composer5, modifierMaterializeModifier, pd.h(companion3, composer5, Integer.valueOf(i48), composer5));
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                MaterialTheme materialTheme = MaterialTheme.INSTANCE;
                int i49 = MaterialTheme.$stable;
                lazyListState4 = lazyListState3;
                composer4 = composer3;
                TextKt.Text-Nvy7gAk("继续观看", PaddingKt.padding-VpY3zN4$default(companion2, f, 0.0f, 2, (Object) null), materialTheme.getColorScheme(composer3, i49).getOnBackground-0d7_KjU(), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer3, i49).getTitleLarge(), composer4, i16, 0, 131064);
                Modifier modifierFillMaxWidth$default = SizeKt.fillMaxWidth$default(companion2, 0.0f, 1, (Object) null);
                Arrangement.HorizontalOrVertical horizontalOrVertical = arrangement.spacedBy-0680j_4(Dp.constructor-impl(24.0f));
                PaddingValues paddingValues = PaddingKt.PaddingValues-YgX7TsA$default(f, 0.0f, 2, (Object) null);
                boolean zChangedInstance2 = composer4.changedInstance(list) | composer4.changedInstance(list4);
                if (i21 == 32) {
                    z7 = true;
                } else {
                    z7 = false;
                }
                boolean z13 = zChangedInstance2 | z7;
                if ((i35 & 7168) == 2048) {
                    z8 = true;
                } else {
                    z8 = false;
                }
                boolean z14 = z13 | z8;
                if ((i35 & 14) == 4) {
                    z9 = true;
                } else {
                    z9 = false;
                }
                boolean z15 = z14 | z9;
                if ((i35 & 112) == 32) {
                    z10 = true;
                } else {
                    z10 = false;
                }
                boolean zChangedInstance3 = z15 | z10 | composer4.changedInstance(coroutineScope) | composer4.changed(lazyListState4);
                if (i32 == 16384) {
                    z11 = true;
                } else {
                    z11 = false;
                }
                z12 = zChangedInstance3 | z11;
                objRememberedValue5 = composer4.rememberedValue();
                if (!z12 || objRememberedValue5 == Composer.Companion.getEmpty()) {
                    final MutableState mutableState18 = mutableState8;
                    final MutableState mutableState19 = mutableState9;
                    final MutableState mutableState20 = mutableState5;
                    final MutableState mutableState21 = mutableState6;
                    final MutableState mutableState22 = mutableState7;
                    Function1 function4 = new Function1() { // from class: eq0
                        public final Object invoke(Object obj2) {
                            LazyListScope lazyListScope = (LazyListScope) obj2;
                            lazyListScope.getClass();
                            final List list5 = list;
                            int size = list5.size();
                            qg qgVar = new qg(list4, 2);
                            final boolean z16 = z;
                            final int i50 = i;
                            final FocusRequester focusRequester2 = focusRequester;
                            final Function3 function5 = function3;
                            final Function1 function6 = function2;
                            final CoroutineScope coroutineScope2 = coroutineScope;
                            final LazyListState lazyListState5 = lazyListState4;
                            final Function0 function7 = function0;
                            final MutableState mutableState23 = mutableState18;
                            final MutableState mutableState24 = mutableState19;
                            final MutableState mutableState25 = mutableState20;
                            final MutableState mutableState26 = mutableState21;
                            final MutableState mutableState27 = mutableState22;
                            d.b(lazyListScope, size, qgVar, (Function1) null, ComposableLambdaKt.composableLambdaInstance(-1573539424, true, new Function4() { // from class: op0
                                public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                                    Object obj7;
                                    final MediaItem mediaItem;
                                    Integer num2;
                                    final int iIntValue2 = ((Integer) obj4).intValue();
                                    Composer composer6 = (Composer) obj5;
                                    int iIntValue3 = ((Integer) obj6).intValue();
                                    ((LazyItemScope) obj3).getClass();
                                    if ((iIntValue3 & 48) == 0) {
                                        iIntValue3 |= composer6.changed(iIntValue2) ? 32 : 16;
                                    }
                                    if (composer6.shouldExecute((iIntValue3 & 145) != 144, iIntValue3 & 1)) {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart(-1573539424, iIntValue3, -1, "com.dh.myembyapp.ui.screens.home.ResumeItemSection.<anonymous>.<anonymous>.<anonymous>.<anonymous> (HomeScreen.kt:3210)");
                                        }
                                        MediaItem mediaItem2 = (MediaItem) list5.get(iIntValue2);
                                        final MutableState mutableState28 = mutableState23;
                                        final boolean z17 = ((Boolean) mutableState28.getValue()).booleanValue() && (num2 = (Integer) mutableState24.getValue()) != null && num2.intValue() == iIntValue2;
                                        Boolean boolValueOf6 = Boolean.valueOf(z17);
                                        Boolean bool2 = (Boolean) mutableState28.getValue();
                                        bool2.getClass();
                                        boolean zChanged3 = composer6.changed(z17);
                                        Object objRememberedValue18 = composer6.rememberedValue();
                                        FocusRequester focusRequester3 = focusRequester2;
                                        if (zChanged3 || objRememberedValue18 == Composer.Companion.getEmpty()) {
                                            objRememberedValue18 = new w2(z17, focusRequester3, mutableState28, null);
                                            composer6.updateRememberedValue(objRememberedValue18);
                                        }
                                        EffectsKt.LaunchedEffect(boolValueOf6, bool2, (Function2) objRememberedValue18, composer6, 0);
                                        if (!z17) {
                                            focusRequester3 = null;
                                        }
                                        boolean zChangedInstance4 = composer6.changedInstance(mediaItem2);
                                        Function3 function8 = function5;
                                        int i51 = iIntValue3 & 112;
                                        boolean zChanged4 = zChangedInstance4 | composer6.changed(function8) | (i51 == 32);
                                        Object objRememberedValue19 = composer6.rememberedValue();
                                        if (zChanged4 || objRememberedValue19 == Composer.Companion.getEmpty()) {
                                            objRememberedValue19 = new uh0(mediaItem2, function8, iIntValue2, 1);
                                            composer6.updateRememberedValue(objRememberedValue19);
                                        }
                                        Function0 function9 = (Function0) objRememberedValue19;
                                        Function1 function10 = function6;
                                        boolean zChanged5 = composer6.changed(function10) | composer6.changedInstance(mediaItem2);
                                        Object objRememberedValue20 = composer6.rememberedValue();
                                        if (zChanged5 || objRememberedValue20 == Composer.Companion.getEmpty()) {
                                            objRememberedValue20 = new k70(function10, mediaItem2, 4);
                                            composer6.updateRememberedValue(objRememberedValue20);
                                        }
                                        Function0 function11 = (Function0) objRememberedValue20;
                                        boolean zChangedInstance5 = composer6.changedInstance(mediaItem2);
                                        boolean z18 = i51 == 32;
                                        final CoroutineScope coroutineScope3 = coroutineScope2;
                                        boolean zChangedInstance6 = zChangedInstance5 | z18 | composer6.changedInstance(coroutineScope3);
                                        final LazyListState lazyListState6 = lazyListState5;
                                        boolean zChanged6 = zChangedInstance6 | composer6.changed(lazyListState6) | composer6.changed(z17);
                                        final Function0 function12 = function7;
                                        boolean zChanged7 = zChanged6 | composer6.changed(function12);
                                        Object objRememberedValue21 = composer6.rememberedValue();
                                        if (zChanged7 || objRememberedValue21 == Composer.Companion.getEmpty()) {
                                            final MutableState mutableState29 = mutableState25;
                                            final MutableState mutableState30 = mutableState26;
                                            final MutableState mutableState31 = mutableState27;
                                            mediaItem = mediaItem2;
                                            obj7 = new Function1() { // from class: tp0
                                                public final Object invoke(Object obj8) {
                                                    Function0 function13;
                                                    boolean zBooleanValue = ((Boolean) obj8).booleanValue();
                                                    MediaItem mediaItem3 = mediaItem;
                                                    MutableState mutableState32 = mutableState29;
                                                    MutableState mutableState33 = mutableState30;
                                                    if (zBooleanValue) {
                                                        mutableState32.setValue(mediaItem3.getId());
                                                        String seriesId = mediaItem3.getSeriesId();
                                                        if (seriesId == null) {
                                                            seriesId = mediaItem3.getId();
                                                        }
                                                        mutableState33.setValue(seriesId);
                                                        if (iIntValue2 == 0) {
                                                            BuildersKt.launch$default(coroutineScope3, (CoroutineContext) null, (CoroutineStart) null, new j7(lazyListState6, 3, null), 3, (Object) null);
                                                        }
                                                    } else if (Intrinsics.areEqual((String) mutableState32.getValue(), mediaItem3.getId())) {
                                                        mutableState32.setValue((Object) null);
                                                        mutableState33.setValue((Object) null);
                                                    }
                                                    if (zBooleanValue && z17) {
                                                        MutableState mutableState34 = mutableState28;
                                                        if (((Boolean) mutableState34.getValue()).booleanValue()) {
                                                            mutableState34.setValue(Boolean.FALSE);
                                                            if (((Boolean) mutableState31.getValue()).booleanValue() && (function13 = function12) != null) {
                                                                function13.invoke();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composer6.updateRememberedValue(obj7);
                                        } else {
                                            obj7 = objRememberedValue21;
                                            mediaItem = mediaItem2;
                                        }
                                        hr0.T(z16, mediaItem, i50, focusRequester3, function9, function11, (Function1) obj7, composer6, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    } else {
                                        composer6.skipToGroupEnd();
                                    }
                                    return Unit.INSTANCE;
                                }
                            }), 4, (Object) null);
                            return Unit.INSTANCE;
                        }
                    };
                    composer2 = composer4;
                    composer2.updateRememberedValue(function4);
                    objRememberedValue5 = function4;
                } else {
                    composer2 = composer4;
                }
                LazyDslKt.LazyRow(modifierFillMaxWidth$default, lazyListState4, paddingValues, false, horizontalOrVertical, (Alignment.Vertical) null, (FlingBehavior) null, false, (OverscrollEffect) null, (Function1) objRememberedValue5, composer2, 24582, 488);
                composer2.endNode();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
            state = stateRememberUpdatedState2;
            state2 = stateRememberUpdatedState;
            mutableState2 = mutableState;
            c = 2;
            i7 = 4;
            list2 = list;
            objJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(list2, "|", null, null, 0, null, new ii0(19), 30, null);
            composerStartRestartGroup.updateRememberedValue(objJoinToString$default);
            String str5 = (String) objJoinToString$default;
            zChangedInstance = composerStartRestartGroup.changedInstance(list2) | composerStartRestartGroup.changed(state2) | composerStartRestartGroup.changed(state) | composerStartRestartGroup.changed(r29);
            objRememberedValue = composerStartRestartGroup.rememberedValue();
            if (zChangedInstance) {
                lazyListState = r29;
                State state4 = state;
                i8 = 0;
                i9 = 1;
                g7 g7Var2 = new g7(list, lazyListState, state2, state4, (Continuation) null, 8);
                composerStartRestartGroup.updateRememberedValue(g7Var2);
                objRememberedValue = g7Var2;
            } else {
                lazyListState = r29;
                State state5 = state;
                i8 = 0;
                i9 = 1;
                g7 g7Var3 = new g7(list, lazyListState, state2, state5, (Continuation) null, 8);
                composerStartRestartGroup.updateRememberedValue(g7Var3);
                objRememberedValue = g7Var3;
            }
            EffectsKt.LaunchedEffect(str5, (Function2) objRememberedValue, composerStartRestartGroup, i8);
            Boolean boolValueOf6 = Boolean.valueOf(z);
            Boolean boolValueOf7 = Boolean.valueOf(z2);
            Object[] objArr3 = new Object[6];
            objArr3[i8] = boolValueOf6;
            objArr3[i9] = str;
            objArr3[c] = num;
            objArr3[3] = str2;
            objArr3[i7] = str3;
            objArr3[5] = boolValueOf7;
            i10 = i36 & 112;
            if (i10 == 32) {
                i11 = i9;
            } else {
                i11 = i8;
            }
            i12 = 234881024 & i36;
            if (i12 == 67108864) {
                i13 = i9;
            } else {
                i13 = i8;
            }
            int i310 = i13 | i11;
            i14 = 3670016 & i36;
            if (i14 == 1048576) {
                i15 = i9;
            } else {
                i15 = i8;
            }
            int i311 = i310 | i15;
            i16 = i36 & 14;
            if (i16 == i7) {
                i17 = i9;
            } else {
                i17 = 0;
            }
            int i312 = i17 | i311;
            i18 = i36 & 7168;
            if (i18 == 2048) {
                i19 = i9;
            } else {
                i19 = 0;
            }
            i20 = i312 | i19;
            Object objRememberedValue18 = composerStartRestartGroup.rememberedValue();
            if (i20 == 0) {
                i21 = i10;
                i22 = i36;
                mutableState3 = mutableState15;
                i23 = ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
                mutableState4 = mutableState14;
                dr0Var = new dr0(z, z2, str3, str, mutableState4, mutableState3, null);
                composerStartRestartGroup.updateRememberedValue(dr0Var);
            } else {
                i21 = i10;
                i22 = i36;
                mutableState3 = mutableState15;
                i23 = ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
                mutableState4 = mutableState14;
                dr0Var = new dr0(z, z2, str3, str, mutableState4, mutableState3, null);
                composerStartRestartGroup.updateRememberedValue(dr0Var);
            }
            EffectsKt.LaunchedEffect(objArr3, (Function2) dr0Var, composerStartRestartGroup, 0);
            Boolean boolValueOf8 = Boolean.valueOf(z);
            Boolean boolValueOf9 = Boolean.valueOf(z2);
            Boolean boolValueOf10 = Boolean.valueOf(z3);
            Object[] objArr4 = new Object[8];
            objArr4[0] = boolValueOf8;
            objArr4[i9] = boolValueOf9;
            objArr4[c] = boolValueOf10;
            objArr4[3] = list;
            objArr4[4] = str;
            objArr4[5] = num;
            objArr4[6] = str2;
            objArr4[7] = str3;
            if (i21 == 32) {
                i24 = i9;
            } else {
                i24 = 0;
            }
            if (i14 == i23) {
                i25 = i9;
            } else {
                i25 = 0;
            }
            int i410 = i24 | i25;
            if (i16 == 4) {
                i26 = i9;
            } else {
                i26 = 0;
            }
            int i411 = i410 | i26;
            if (i18 == 2048) {
                i27 = i9;
            } else {
                i27 = 0;
            }
            int i412 = i411 | i27;
            if (i12 == 67108864) {
                i28 = i9;
            } else {
                i28 = 0;
            }
            int i413 = i412 | i28;
            if ((i22 & 1879048192) == 536870912) {
                i29 = i9;
            } else {
                i29 = 0;
            }
            int i414 = i413 | i29;
            if ((i22 & 57344) == 16384) {
                i30 = i9;
            } else {
                i30 = 0;
            }
            int i415 = i414 | i30 | (composerStartRestartGroup.changedInstance(list) ? 1 : 0);
            if ((i22 & 458752) == 131072) {
                i31 = i9;
            } else {
                i31 = 0;
            }
            int i416 = i415 | i31;
            lazyListState2 = lazyListState;
            int i417 = i416 | (composerStartRestartGroup.changed(lazyListState2) ? 1 : 0);
            i32 = i35 & 57344;
            if (i32 == 16384) {
                i33 = i9;
            } else {
                i33 = 0;
            }
            i34 = i417 | i33;
            objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (i34 == 0) {
                fr0 fr0Var2 = new fr0(z, str3, str, z2, z3, num, list, str2, lazyListState2, function0, mutableState4, mutableState3, mutableState12, mutableState13, mutableState16, mutableState10, mutableState11, null);
                list3 = list;
                lazyListState3 = lazyListState2;
                mutableState5 = mutableState12;
                mutableState6 = mutableState13;
                mutableState7 = mutableState16;
                mutableState8 = mutableState10;
                mutableState9 = mutableState11;
                composer3 = composerStartRestartGroup;
                composer3.updateRememberedValue(fr0Var2);
                objRememberedValue2 = fr0Var2;
            } else {
                fr0 fr0Var3 = new fr0(z, str3, str, z2, z3, num, list, str2, lazyListState2, function0, mutableState4, mutableState3, mutableState12, mutableState13, mutableState16, mutableState10, mutableState11, null);
                list3 = list;
                lazyListState3 = lazyListState2;
                mutableState5 = mutableState12;
                mutableState6 = mutableState13;
                mutableState7 = mutableState16;
                mutableState8 = mutableState10;
                mutableState9 = mutableState11;
                composer3 = composerStartRestartGroup;
                composer3.updateRememberedValue(fr0Var3);
                objRememberedValue2 = fr0Var3;
            }
            EffectsKt.LaunchedEffect(objArr4, (Function2) objRememberedValue2, composer3, 0);
            zChanged = composer3.changed(list3);
            objRememberedValue3 = composer3.rememberedValue();
            if (zChanged) {
                obj = objRememberedValue3;
                linkedHashMap = new LinkedHashMap();
                arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                it = list3.iterator();
                while (it.hasNext()) {
                    id = ((MediaItem) it.next()).getId();
                    iIntValue = ((Number) j$.util.Map.-EL.getOrDefault(linkedHashMap, id, 0)).intValue();
                    linkedHashMap.put(id, Integer.valueOf(iIntValue + 1));
                    if (iIntValue != 0) {
                        id = id + "_dup" + iIntValue;
                    }
                    arrayList.add(id);
                }
                composer3.updateRememberedValue(arrayList);
                obj = arrayList;
            } else {
                obj = objRememberedValue3;
                linkedHashMap = new LinkedHashMap();
                arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                it = list3.iterator();
                while (it.hasNext()) {
                    id = ((MediaItem) it.next()).getId();
                    iIntValue = ((Number) j$.util.Map.-EL.getOrDefault(linkedHashMap, id, 0)).intValue();
                    linkedHashMap.put(id, Integer.valueOf(iIntValue + 1));
                    if (iIntValue != 0) {
                        id = id + "_dup" + iIntValue;
                    }
                    arrayList.add(id);
                }
                composer3.updateRememberedValue(arrayList);
                obj = arrayList;
            }
            obj = objRememberedValue3;
            list4 = (List) obj;
            Modifier.Companion companion4 = Modifier.Companion;
            Modifier modifierFocusGroup2 = FocusableKt.focusGroup(SizeKt.fillMaxWidth$default(companion4, 0.0f, 1, (Object) null));
            if ((i22 & 29360128) == 8388608) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (i16 == 4) {
                z5 = true;
            } else {
                z5 = false;
            }
            z6 = z4 | z5;
            objRememberedValue4 = composer3.rememberedValue();
            if (z6) {
                objRememberedValue4 = new y7(function1, mutableState2, 9);
                composer3.updateRememberedValue(objRememberedValue4);
            } else {
                objRememberedValue4 = new y7(function1, mutableState2, 9);
                composer3.updateRememberedValue(objRememberedValue4);
            }
            Modifier modifierOnFocusChanged2 = FocusChangedModifierKt.onFocusChanged(modifierFocusGroup2, (Function1) objRememberedValue4);
            Arrangement arrangement2 = Arrangement.INSTANCE;
            MeasurePolicy measurePolicyColumnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement2.spacedBy-0680j_4(uoVar.a(Dp.constructor-impl(11.0f))), Alignment.Companion.getStart(), composer3, 0);
            long currentCompositeKeyHashCode2 = ComposablesKt.getCurrentCompositeKeyHashCode(composer3, 0);
            int i418 = (int) (currentCompositeKeyHashCode2 ^ (currentCompositeKeyHashCode2 >>> 32));
            CompositionLocalMap currentCompositionLocalMap2 = composer3.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer3, modifierOnFocusChanged2);
            ComposeUiNode.Companion companion5 = ComposeUiNode.Companion;
            constructor = companion5.getConstructor();
            if (composer3.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composer3.startReusableNode();
            if (composer3.getInserting()) {
                composer3.createNode(constructor);
            } else {
                composer3.useNode();
            }
            Composer composer6 = Updater.constructor-impl(composer3);
            zr.z(companion5, composer6, measurePolicyColumnMeasurePolicy2, composer6, currentCompositionLocalMap2);
            Updater.set-impl(composer6, modifierMaterializeModifier2, pd.h(companion5, composer6, Integer.valueOf(i418), composer6));
            ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
            MaterialTheme materialTheme2 = MaterialTheme.INSTANCE;
            int i419 = MaterialTheme.$stable;
            lazyListState4 = lazyListState3;
            composer4 = composer3;
            TextKt.Text-Nvy7gAk("继续观看", PaddingKt.padding-VpY3zN4$default(companion4, f, 0.0f, 2, (Object) null), materialTheme2.getColorScheme(composer3, i419).getOnBackground-0d7_KjU(), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme2.getTypography(composer3, i419).getTitleLarge(), composer4, i16, 0, 131064);
            Modifier modifierFillMaxWidth$default2 = SizeKt.fillMaxWidth$default(companion4, 0.0f, 1, (Object) null);
            Arrangement.HorizontalOrVertical horizontalOrVertical2 = arrangement2.spacedBy-0680j_4(Dp.constructor-impl(24.0f));
            PaddingValues paddingValues2 = PaddingKt.PaddingValues-YgX7TsA$default(f, 0.0f, 2, (Object) null);
            boolean zChangedInstance4 = composer4.changedInstance(list) | composer4.changedInstance(list4);
            if (i21 == 32) {
                z7 = true;
            } else {
                z7 = false;
            }
            boolean z16 = zChangedInstance4 | z7;
            if ((i35 & 7168) == 2048) {
                z8 = true;
            } else {
                z8 = false;
            }
            boolean z17 = z16 | z8;
            if ((i35 & 14) == 4) {
                z9 = true;
            } else {
                z9 = false;
            }
            boolean z18 = z17 | z9;
            if ((i35 & 112) == 32) {
                z10 = true;
            } else {
                z10 = false;
            }
            boolean zChangedInstance5 = z18 | z10 | composer4.changedInstance(coroutineScope) | composer4.changed(lazyListState4);
            if (i32 == 16384) {
                z11 = true;
            } else {
                z11 = false;
            }
            z12 = zChangedInstance5 | z11;
            objRememberedValue5 = composer4.rememberedValue();
            if (z12) {
                final MutableState mutableState110 = mutableState8;
                final MutableState mutableState111 = mutableState9;
                final MutableState mutableState23 = mutableState5;
                final MutableState mutableState24 = mutableState6;
                final MutableState mutableState25 = mutableState7;
                Function1 function5 = new Function1() { // from class: eq0
                    public final Object invoke(Object obj2) {
                        LazyListScope lazyListScope = (LazyListScope) obj2;
                        lazyListScope.getClass();
                        final List list5 = list;
                        int size = list5.size();
                        qg qgVar = new qg(list4, 2);
                        final boolean z19 = z;
                        final int i50 = i;
                        final FocusRequester focusRequester2 = focusRequester;
                        final Function3 function6 = function3;
                        final Function1 function7 = function2;
                        final CoroutineScope coroutineScope2 = coroutineScope;
                        final LazyListState lazyListState5 = lazyListState4;
                        final Function0 function8 = function0;
                        final MutableState mutableState26 = mutableState110;
                        final MutableState mutableState27 = mutableState111;
                        final MutableState mutableState28 = mutableState23;
                        final MutableState mutableState29 = mutableState24;
                        final MutableState mutableState210 = mutableState25;
                        d.b(lazyListScope, size, qgVar, (Function1) null, ComposableLambdaKt.composableLambdaInstance(-1573539424, true, new Function4() { // from class: op0
                            public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                                Object obj7;
                                final MediaItem mediaItem;
                                Integer num2;
                                final int iIntValue2 = ((Integer) obj4).intValue();
                                Composer composer7 = (Composer) obj5;
                                int iIntValue3 = ((Integer) obj6).intValue();
                                ((LazyItemScope) obj3).getClass();
                                if ((iIntValue3 & 48) == 0) {
                                    iIntValue3 |= composer7.changed(iIntValue2) ? 32 : 16;
                                }
                                if (composer7.shouldExecute((iIntValue3 & 145) != 144, iIntValue3 & 1)) {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart(-1573539424, iIntValue3, -1, "com.dh.myembyapp.ui.screens.home.ResumeItemSection.<anonymous>.<anonymous>.<anonymous>.<anonymous> (HomeScreen.kt:3210)");
                                    }
                                    MediaItem mediaItem2 = (MediaItem) list5.get(iIntValue2);
                                    final MutableState mutableState211 = mutableState26;
                                    final boolean z110 = ((Boolean) mutableState211.getValue()).booleanValue() && (num2 = (Integer) mutableState27.getValue()) != null && num2.intValue() == iIntValue2;
                                    Boolean boolValueOf11 = Boolean.valueOf(z110);
                                    Boolean bool2 = (Boolean) mutableState211.getValue();
                                    bool2.getClass();
                                    boolean zChanged3 = composer7.changed(z110);
                                    Object objRememberedValue19 = composer7.rememberedValue();
                                    FocusRequester focusRequester3 = focusRequester2;
                                    if (zChanged3 || objRememberedValue19 == Composer.Companion.getEmpty()) {
                                        objRememberedValue19 = new w2(z110, focusRequester3, mutableState211, null);
                                        composer7.updateRememberedValue(objRememberedValue19);
                                    }
                                    EffectsKt.LaunchedEffect(boolValueOf11, bool2, (Function2) objRememberedValue19, composer7, 0);
                                    if (!z110) {
                                        focusRequester3 = null;
                                    }
                                    boolean zChangedInstance6 = composer7.changedInstance(mediaItem2);
                                    Function3 function9 = function6;
                                    int i51 = iIntValue3 & 112;
                                    boolean zChanged4 = zChangedInstance6 | composer7.changed(function9) | (i51 == 32);
                                    Object objRememberedValue110 = composer7.rememberedValue();
                                    if (zChanged4 || objRememberedValue110 == Composer.Companion.getEmpty()) {
                                        objRememberedValue110 = new uh0(mediaItem2, function9, iIntValue2, 1);
                                        composer7.updateRememberedValue(objRememberedValue110);
                                    }
                                    Function0 function10 = (Function0) objRememberedValue110;
                                    Function1 function11 = function7;
                                    boolean zChanged5 = composer7.changed(function11) | composer7.changedInstance(mediaItem2);
                                    Object objRememberedValue20 = composer7.rememberedValue();
                                    if (zChanged5 || objRememberedValue20 == Composer.Companion.getEmpty()) {
                                        objRememberedValue20 = new k70(function11, mediaItem2, 4);
                                        composer7.updateRememberedValue(objRememberedValue20);
                                    }
                                    Function0 function12 = (Function0) objRememberedValue20;
                                    boolean zChangedInstance7 = composer7.changedInstance(mediaItem2);
                                    boolean z111 = i51 == 32;
                                    final CoroutineScope coroutineScope3 = coroutineScope2;
                                    boolean zChangedInstance8 = zChangedInstance7 | z111 | composer7.changedInstance(coroutineScope3);
                                    final LazyListState lazyListState6 = lazyListState5;
                                    boolean zChanged6 = zChangedInstance8 | composer7.changed(lazyListState6) | composer7.changed(z110);
                                    final Function0 function13 = function8;
                                    boolean zChanged7 = zChanged6 | composer7.changed(function13);
                                    Object objRememberedValue21 = composer7.rememberedValue();
                                    if (zChanged7 || objRememberedValue21 == Composer.Companion.getEmpty()) {
                                        final MutableState mutableState212 = mutableState28;
                                        final MutableState mutableState30 = mutableState29;
                                        final MutableState mutableState31 = mutableState210;
                                        mediaItem = mediaItem2;
                                        obj7 = new Function1() { // from class: tp0
                                            public final Object invoke(Object obj8) {
                                                Function0 function14;
                                                boolean zBooleanValue = ((Boolean) obj8).booleanValue();
                                                MediaItem mediaItem3 = mediaItem;
                                                MutableState mutableState32 = mutableState212;
                                                MutableState mutableState33 = mutableState30;
                                                if (zBooleanValue) {
                                                    mutableState32.setValue(mediaItem3.getId());
                                                    String seriesId = mediaItem3.getSeriesId();
                                                    if (seriesId == null) {
                                                        seriesId = mediaItem3.getId();
                                                    }
                                                    mutableState33.setValue(seriesId);
                                                    if (iIntValue2 == 0) {
                                                        BuildersKt.launch$default(coroutineScope3, (CoroutineContext) null, (CoroutineStart) null, new j7(lazyListState6, 3, null), 3, (Object) null);
                                                    }
                                                } else if (Intrinsics.areEqual((String) mutableState32.getValue(), mediaItem3.getId())) {
                                                    mutableState32.setValue((Object) null);
                                                    mutableState33.setValue((Object) null);
                                                }
                                                if (zBooleanValue && z110) {
                                                    MutableState mutableState34 = mutableState211;
                                                    if (((Boolean) mutableState34.getValue()).booleanValue()) {
                                                        mutableState34.setValue(Boolean.FALSE);
                                                        if (((Boolean) mutableState31.getValue()).booleanValue() && (function14 = function13) != null) {
                                                            function14.invoke();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composer7.updateRememberedValue(obj7);
                                    } else {
                                        obj7 = objRememberedValue21;
                                        mediaItem = mediaItem2;
                                    }
                                    hr0.T(z19, mediaItem, i50, focusRequester3, function10, function12, (Function1) obj7, composer7, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                } else {
                                    composer7.skipToGroupEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }), 4, (Object) null);
                        return Unit.INSTANCE;
                    }
                };
                composer2 = composer4;
                composer2.updateRememberedValue(function5);
                objRememberedValue5 = function5;
            } else {
                final MutableState mutableState112 = mutableState8;
                final MutableState mutableState113 = mutableState9;
                final MutableState mutableState26 = mutableState5;
                final MutableState mutableState27 = mutableState6;
                final MutableState mutableState28 = mutableState7;
                Function1 function6 = new Function1() { // from class: eq0
                    public final Object invoke(Object obj2) {
                        LazyListScope lazyListScope = (LazyListScope) obj2;
                        lazyListScope.getClass();
                        final List list5 = list;
                        int size = list5.size();
                        qg qgVar = new qg(list4, 2);
                        final boolean z19 = z;
                        final int i50 = i;
                        final FocusRequester focusRequester2 = focusRequester;
                        final Function3 function7 = function3;
                        final Function1 function8 = function2;
                        final CoroutineScope coroutineScope2 = coroutineScope;
                        final LazyListState lazyListState5 = lazyListState4;
                        final Function0 function9 = function0;
                        final MutableState mutableState29 = mutableState112;
                        final MutableState mutableState210 = mutableState113;
                        final MutableState mutableState211 = mutableState26;
                        final MutableState mutableState212 = mutableState27;
                        final MutableState mutableState213 = mutableState28;
                        d.b(lazyListScope, size, qgVar, (Function1) null, ComposableLambdaKt.composableLambdaInstance(-1573539424, true, new Function4() { // from class: op0
                            public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                                Object obj7;
                                final MediaItem mediaItem;
                                Integer num2;
                                final int iIntValue2 = ((Integer) obj4).intValue();
                                Composer composer7 = (Composer) obj5;
                                int iIntValue3 = ((Integer) obj6).intValue();
                                ((LazyItemScope) obj3).getClass();
                                if ((iIntValue3 & 48) == 0) {
                                    iIntValue3 |= composer7.changed(iIntValue2) ? 32 : 16;
                                }
                                if (composer7.shouldExecute((iIntValue3 & 145) != 144, iIntValue3 & 1)) {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart(-1573539424, iIntValue3, -1, "com.dh.myembyapp.ui.screens.home.ResumeItemSection.<anonymous>.<anonymous>.<anonymous>.<anonymous> (HomeScreen.kt:3210)");
                                    }
                                    MediaItem mediaItem2 = (MediaItem) list5.get(iIntValue2);
                                    final MutableState mutableState214 = mutableState29;
                                    final boolean z110 = ((Boolean) mutableState214.getValue()).booleanValue() && (num2 = (Integer) mutableState210.getValue()) != null && num2.intValue() == iIntValue2;
                                    Boolean boolValueOf11 = Boolean.valueOf(z110);
                                    Boolean bool2 = (Boolean) mutableState214.getValue();
                                    bool2.getClass();
                                    boolean zChanged3 = composer7.changed(z110);
                                    Object objRememberedValue19 = composer7.rememberedValue();
                                    FocusRequester focusRequester3 = focusRequester2;
                                    if (zChanged3 || objRememberedValue19 == Composer.Companion.getEmpty()) {
                                        objRememberedValue19 = new w2(z110, focusRequester3, mutableState214, null);
                                        composer7.updateRememberedValue(objRememberedValue19);
                                    }
                                    EffectsKt.LaunchedEffect(boolValueOf11, bool2, (Function2) objRememberedValue19, composer7, 0);
                                    if (!z110) {
                                        focusRequester3 = null;
                                    }
                                    boolean zChangedInstance6 = composer7.changedInstance(mediaItem2);
                                    Function3 function10 = function7;
                                    int i51 = iIntValue3 & 112;
                                    boolean zChanged4 = zChangedInstance6 | composer7.changed(function10) | (i51 == 32);
                                    Object objRememberedValue110 = composer7.rememberedValue();
                                    if (zChanged4 || objRememberedValue110 == Composer.Companion.getEmpty()) {
                                        objRememberedValue110 = new uh0(mediaItem2, function10, iIntValue2, 1);
                                        composer7.updateRememberedValue(objRememberedValue110);
                                    }
                                    Function0 function11 = (Function0) objRememberedValue110;
                                    Function1 function12 = function8;
                                    boolean zChanged5 = composer7.changed(function12) | composer7.changedInstance(mediaItem2);
                                    Object objRememberedValue20 = composer7.rememberedValue();
                                    if (zChanged5 || objRememberedValue20 == Composer.Companion.getEmpty()) {
                                        objRememberedValue20 = new k70(function12, mediaItem2, 4);
                                        composer7.updateRememberedValue(objRememberedValue20);
                                    }
                                    Function0 function13 = (Function0) objRememberedValue20;
                                    boolean zChangedInstance7 = composer7.changedInstance(mediaItem2);
                                    boolean z111 = i51 == 32;
                                    final CoroutineScope coroutineScope3 = coroutineScope2;
                                    boolean zChangedInstance8 = zChangedInstance7 | z111 | composer7.changedInstance(coroutineScope3);
                                    final LazyListState lazyListState6 = lazyListState5;
                                    boolean zChanged6 = zChangedInstance8 | composer7.changed(lazyListState6) | composer7.changed(z110);
                                    final Function0 function14 = function9;
                                    boolean zChanged7 = zChanged6 | composer7.changed(function14);
                                    Object objRememberedValue21 = composer7.rememberedValue();
                                    if (zChanged7 || objRememberedValue21 == Composer.Companion.getEmpty()) {
                                        final MutableState mutableState215 = mutableState211;
                                        final MutableState mutableState30 = mutableState212;
                                        final MutableState mutableState31 = mutableState213;
                                        mediaItem = mediaItem2;
                                        obj7 = new Function1() { // from class: tp0
                                            public final Object invoke(Object obj8) {
                                                Function0 function15;
                                                boolean zBooleanValue = ((Boolean) obj8).booleanValue();
                                                MediaItem mediaItem3 = mediaItem;
                                                MutableState mutableState32 = mutableState215;
                                                MutableState mutableState33 = mutableState30;
                                                if (zBooleanValue) {
                                                    mutableState32.setValue(mediaItem3.getId());
                                                    String seriesId = mediaItem3.getSeriesId();
                                                    if (seriesId == null) {
                                                        seriesId = mediaItem3.getId();
                                                    }
                                                    mutableState33.setValue(seriesId);
                                                    if (iIntValue2 == 0) {
                                                        BuildersKt.launch$default(coroutineScope3, (CoroutineContext) null, (CoroutineStart) null, new j7(lazyListState6, 3, null), 3, (Object) null);
                                                    }
                                                } else if (Intrinsics.areEqual((String) mutableState32.getValue(), mediaItem3.getId())) {
                                                    mutableState32.setValue((Object) null);
                                                    mutableState33.setValue((Object) null);
                                                }
                                                if (zBooleanValue && z110) {
                                                    MutableState mutableState34 = mutableState214;
                                                    if (((Boolean) mutableState34.getValue()).booleanValue()) {
                                                        mutableState34.setValue(Boolean.FALSE);
                                                        if (((Boolean) mutableState31.getValue()).booleanValue() && (function15 = function14) != null) {
                                                            function15.invoke();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composer7.updateRememberedValue(obj7);
                                    } else {
                                        obj7 = objRememberedValue21;
                                        mediaItem = mediaItem2;
                                    }
                                    hr0.T(z19, mediaItem, i50, focusRequester3, function11, function13, (Function1) obj7, composer7, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                } else {
                                    composer7.skipToGroupEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }), 4, (Object) null);
                        return Unit.INSTANCE;
                    }
                };
                composer2 = composer4;
                composer2.updateRememberedValue(function6);
                objRememberedValue5 = function6;
            }
            LazyDslKt.LazyRow(modifierFillMaxWidth$default2, lazyListState4, paddingValues2, false, horizontalOrVertical2, (Alignment.Vertical) null, (FlingBehavior) null, false, (OverscrollEffect) null, (Function1) objRememberedValue5, composer2, 24582, 488);
            composer2.endNode();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: fq0
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i2 | 1);
                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i3);
                    hr0.X(z, list, str, num, str2, str3, function1, z2, z3, function3, function2, f, i, function0, (Composer) obj2, iUpdateChangedFlags, iUpdateChangedFlags2);
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public static final void Y(final ItemCounts itemCounts, final float f, Composer composer, final int i) {
        Composer composerStartRestartGroup = composer.startRestartGroup(192650726);
        int i2 = (i & 6) == 0 ? (composerStartRestartGroup.changed(itemCounts) ? 4 : 2) | i : i;
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changed(f) ? 32 : 16;
        }
        final int i3 = 0;
        if (composerStartRestartGroup.shouldExecute((i2 & 19) != 18, i2 & 1)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(192650726, i2, -1, "com.dh.myembyapp.ui.screens.home.ServerItemCountsSection (HomeScreen.kt:2268)");
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-175699322, 6, -1, "com.dh.myembyapp.ui.theme.MyEmbyTheme.<get-colors> (Theme.kt:122)");
            }
            g81 g81Var = (g81) composerStartRestartGroup.consume(k92.b);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            if (itemCounts.getMovieCount() == 0 && itemCounts.getSeriesCount() == 0 && itemCounts.getEpisodeCount() == 0) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composerStartRestartGroup.endRestartGroup();
                if (scopeUpdateScopeEndRestartGroup != null) {
                    scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: vp0
                        public final Object invoke(Object obj, Object obj2) {
                            int i4 = i3;
                            Composer composer2 = (Composer) obj;
                            ((Integer) obj2).intValue();
                            switch (i4) {
                                case 0:
                                    hr0.Y(itemCounts, f, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                    break;
                                default:
                                    hr0.Y(itemCounts, f, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
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
            Modifier modifier = PaddingKt.padding-VpY3zN4(SizeKt.fillMaxWidth$default(companion2, 0.0f, 1, (Object) null), f, Dp.constructor-impl(12.0f));
            Alignment.Companion companion3 = Alignment.Companion;
            Alignment.Horizontal centerHorizontally = companion3.getCenterHorizontally();
            Arrangement arrangement = Arrangement.INSTANCE;
            int i4 = 54;
            MeasurePolicy measurePolicyP = zr.p(12.0f, arrangement, centerHorizontally, composerStartRestartGroup, 54);
            long currentCompositeKeyHashCode = ComposablesKt.getCurrentCompositeKeyHashCode(composerStartRestartGroup, 0);
            float f2 = 12.0f;
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
            Composer composer2 = Updater.constructor-impl(composerStartRestartGroup);
            zr.z(companion4, composer2, measurePolicyP, composer2, currentCompositionLocalMap);
            Updater.set-impl(composer2, modifierMaterializeModifier, pd.h(companion4, composer2, Integer.valueOf(i5), composer2));
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            MeasurePolicy measurePolicyRowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement.spacedBy-0680j_4(Dp.constructor-impl(16.0f)), companion3.getCenterVertically(), composerStartRestartGroup, 54);
            long currentCompositeKeyHashCode2 = ComposablesKt.getCurrentCompositeKeyHashCode(composerStartRestartGroup, 0);
            int i6 = (int) ((currentCompositeKeyHashCode2 >>> 32) ^ currentCompositeKeyHashCode2);
            CompositionLocalMap currentCompositionLocalMap2 = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerStartRestartGroup, companion2);
            Function0 constructor2 = companion4.getConstructor();
            if (composerStartRestartGroup.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composerStartRestartGroup.startReusableNode();
            if (composerStartRestartGroup.getInserting()) {
                composerStartRestartGroup.createNode(constructor2);
            } else {
                composerStartRestartGroup.useNode();
            }
            Composer composer3 = Updater.constructor-impl(composerStartRestartGroup);
            zr.z(companion4, composer3, measurePolicyRowMeasurePolicy, composer3, currentCompositionLocalMap2);
            Updater.set-impl(composer3, modifierMaterializeModifier2, pd.h(companion4, composer3, Integer.valueOf(i6), composer3));
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            composerStartRestartGroup.startReplaceGroup(-357110516);
            for (Triple triple : list) {
                String str = (String) triple.component1();
                String str2 = (String) triple.component2();
                long j = ((Color) triple.component3()).unbox-impl();
                Modifier modifier2 = BackgroundKt.background-bw27NRU$default(ClipKt.clip(SizeKt.width-3ABfNKs(Modifier.Companion, Dp.constructor-impl(118.0f)), RoundedCornerShapeKt.RoundedCornerShape-0680j_4(Dp.constructor-impl(18.0f))), g81Var.C, (Shape) null, 2, (Object) null);
                float f3 = Dp.constructor-impl(1.0f);
                MaterialTheme materialTheme = MaterialTheme.INSTANCE;
                int i7 = MaterialTheme.$stable;
                Modifier modifier3 = PaddingKt.padding-VpY3zN4(BorderKt.border-xT4_qwU(modifier2, f3, materialTheme.getColorScheme(composerStartRestartGroup, i7).getOutlineVariant-0d7_KjU(), RoundedCornerShapeKt.RoundedCornerShape-0680j_4(Dp.constructor-impl(18.0f))), Dp.constructor-impl(f2), Dp.constructor-impl(10.0f));
                MeasurePolicy measurePolicyP2 = zr.p(4.0f, Arrangement.INSTANCE, Alignment.Companion.getCenterHorizontally(), composerStartRestartGroup, i4);
                long currentCompositeKeyHashCode3 = ComposablesKt.getCurrentCompositeKeyHashCode(composerStartRestartGroup, 0);
                int i8 = (int) (currentCompositeKeyHashCode3 ^ (currentCompositeKeyHashCode3 >>> 32));
                CompositionLocalMap currentCompositionLocalMap3 = composerStartRestartGroup.getCurrentCompositionLocalMap();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifier3);
                ComposeUiNode.Companion companion5 = ComposeUiNode.Companion;
                Function0 constructor3 = companion5.getConstructor();
                if (composerStartRestartGroup.getApplier() == null) {
                    ComposablesKt.invalidApplier();
                }
                composerStartRestartGroup.startReusableNode();
                if (composerStartRestartGroup.getInserting()) {
                    composerStartRestartGroup.createNode(constructor3);
                } else {
                    composerStartRestartGroup.useNode();
                }
                Composer composer4 = Updater.constructor-impl(composerStartRestartGroup);
                zr.z(companion5, composer4, measurePolicyP2, composer4, currentCompositionLocalMap3);
                Updater.set-impl(composer4, modifierMaterializeModifier3, pd.h(companion5, composer4, Integer.valueOf(i8), composer4));
                ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                str.getClass();
                Composer composer5 = composerStartRestartGroup;
                TextKt.Text-Nvy7gAk(str, (Modifier) null, j, (TextAutoSize) null, TextUnitKt.getSp(19), (FontStyle) null, FontWeight.Companion.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, (TextStyle) null, composer5, 1597440, 0, 262058);
                TextKt.Text-Nvy7gAk(str2, (Modifier) null, Color.copy-wmQWz5c$default(materialTheme.getColorScheme(composer5, i7).getOnSurface-0d7_KjU(), 0.72f, 0.0f, 0.0f, 0.0f, 14, (Object) null), (TextAutoSize) null, TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, (TextStyle) null, composer5, 24576, 0, 262122);
                composerStartRestartGroup = composer5;
                composerStartRestartGroup.endNode();
                i4 = i4;
                f2 = f2;
                g81Var = g81Var;
            }
            composerStartRestartGroup.endReplaceGroup();
            composerStartRestartGroup.endNode();
            SpacerKt.Spacer(SizeKt.height-3ABfNKs(Modifier.Companion, Dp.constructor-impl(10.0f)), composerStartRestartGroup, 6);
            composerStartRestartGroup.endNode();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerStartRestartGroup.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup2 = composerStartRestartGroup.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup2 != null) {
            final int i9 = 1;
            scopeUpdateScopeEndRestartGroup2.updateScope(new Function2() { // from class: vp0
                public final Object invoke(Object obj, Object obj2) {
                    int i10 = i9;
                    Composer composer6 = (Composer) obj;
                    ((Integer) obj2).intValue();
                    switch (i10) {
                        case 0:
                            hr0.Y(itemCounts, f, composer6, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                        default:
                            hr0.Y(itemCounts, f, composer6, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public static final void Z(List list, MutableState mutableState, MutableState mutableState2, MutableState mutableState3, MutableState mutableState4, Map map, fs0 fs0Var) throws NoSuchAlgorithmException {
        if (((Boolean) mutableState.getValue()).booleanValue() || ((Number) mutableState2.getValue()).intValue() >= list.size()) {
            return;
        }
        int iIntValue = ((Number) mutableState2.getValue()).intValue();
        int iCoerceAtMost = RangesKt.coerceAtMost(((Number) mutableState2.getValue()).intValue() + 5, list.size());
        List<Library> listSubList = list.subList(iIntValue, iCoerceAtMost);
        if (listSubList.isEmpty()) {
            return;
        }
        mutableState3.setValue(Integer.valueOf(iIntValue));
        mutableState4.setValue(Integer.valueOf(iCoerceAtMost));
        mutableState.setValue(Boolean.TRUE);
        for (Library library : listSubList) {
            if (map.get(library.getId()) == null) {
                fs0Var.H(library.getId());
            }
        }
    }

    /* JADX WARN: Failed to calculate best type for var: r7v31 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r7v31 ??, new type: boolean
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r8v59 ??, new type: boolean
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:186)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:245)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException
        */
    public static final void a(boolean r81, java.util.List r82, java.util.List r83, java.util.List r84, java.lang.String r85, java.lang.Integer r86, java.lang.String r87, java.lang.String r88, boolean r89, boolean r90, kotlin.jvm.functions.Function1 r91, kotlin.jvm.functions.Function2 r92, kotlin.jvm.functions.Function3 r93, kotlin.jvm.functions.Function2 r94, kotlin.jvm.functions.Function2 r95, kotlin.jvm.functions.Function3 r96, kotlin.jvm.functions.Function0 r97, defpackage.fs0 r98, boolean r99, boolean r100, com.dh.myembyapp.data.model.ItemCounts r101, kotlin.jvm.functions.Function1 r102, kotlin.jvm.functions.Function1 r103, int r104, kotlin.jvm.functions.Function0 r105, androidx.compose.runtime.Composer r106, int r107, int r108, int r109) {
        /*
            Method dump skipped, instruction units count: 4746
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.hr0.a(boolean, java.util.List, java.util.List, java.util.List, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, boolean, boolean, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function0, fs0, boolean, boolean, com.dh.myembyapp.data.model.ItemCounts, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, int, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int, int):void");
    }

    public static final LazyListState a0(Composer composer, String str) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(298680679, 0, -1, "com.dh.myembyapp.ui.screens.home.rememberSavedLazyRowState (HomeScreen.kt:2328)");
        }
        Object[] objArr = {str};
        Object objRememberedValue = composer.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (objRememberedValue == companion.getEmpty()) {
            objRememberedValue = new hp0(7);
            composer.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) RememberSaveableKt.rememberSaveable(objArr, (Function0) objRememberedValue, composer, 48);
        Object[] objArr2 = {str};
        Object objRememberedValue2 = composer.rememberedValue();
        if (objRememberedValue2 == companion.getEmpty()) {
            objRememberedValue2 = new hp0(8);
            composer.updateRememberedValue(objRememberedValue2);
        }
        MutableState mutableState2 = (MutableState) RememberSaveableKt.rememberSaveable(objArr2, (Function0) objRememberedValue2, composer, 48);
        LazyListState lazyListStateRememberLazyListState = LazyListStateKt.rememberLazyListState(((Number) mutableState.getValue()).intValue(), ((Number) mutableState2.getValue()).intValue(), composer, 0, 0);
        boolean zChanged = composer.changed(lazyListStateRememberLazyListState) | composer.changed(mutableState) | composer.changed(mutableState2);
        Object objRememberedValue3 = composer.rememberedValue();
        if (zChanged || objRememberedValue3 == companion.getEmpty()) {
            wy wyVar = new wy(lazyListStateRememberLazyListState, mutableState, mutableState2, null, 1);
            composer.updateRememberedValue(wyVar);
            objRememberedValue3 = wyVar;
        }
        EffectsKt.LaunchedEffect(lazyListStateRememberLazyListState, (Function2) objRememberedValue3, composer, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return lazyListStateRememberLazyListState;
    }

    public static final int b(MutableState mutableState) {
        return ((Number) mutableState.getValue()).intValue();
    }

    public static final int c(MutableState mutableState) {
        return ((Number) mutableState.getValue()).intValue();
    }

    public static final int d(MutableState mutableState) {
        return ((Number) mutableState.getValue()).intValue();
    }

    public static final boolean e(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    /* JADX WARN: Code duplicated, block: B:1000:0x205e  */
    /* JADX WARN: Code duplicated, block: B:1001:0x206d  */
    /* JADX WARN: Code duplicated, block: B:1004:0x2081  */
    /* JADX WARN: Code duplicated, block: B:1005:0x2090  */
    /* JADX WARN: Code duplicated, block: B:1008:0x20a4  */
    /* JADX WARN: Code duplicated, block: B:1009:0x20b3  */
    /* JADX WARN: Code duplicated, block: B:1012:0x20c5  */
    /* JADX WARN: Code duplicated, block: B:1013:0x20d4  */
    /* JADX WARN: Code duplicated, block: B:1016:0x20e8  */
    /* JADX WARN: Code duplicated, block: B:1017:0x20f7  */
    /* JADX WARN: Code duplicated, block: B:1024:0x211f  */
    /* JADX WARN: Code duplicated, block: B:1026:0x216d  */
    /* JADX WARN: Code duplicated, block: B:1029:0x219e  */
    /* JADX WARN: Code duplicated, block: B:1032:0x21bb  */
    /* JADX WARN: Code duplicated, block: B:1034:0x21cb  */
    /* JADX WARN: Code duplicated, block: B:1036:0x21ec  */
    /* JADX WARN: Code duplicated, block: B:1039:0x2202  */
    /* JADX WARN: Code duplicated, block: B:1041:0x2212  */
    /* JADX WARN: Code duplicated, block: B:1043:0x2228  */
    /* JADX WARN: Code duplicated, block: B:1046:0x223e  */
    /* JADX WARN: Code duplicated, block: B:1048:0x224e  */
    /* JADX WARN: Code duplicated, block: B:1055:0x2276  */
    /* JADX WARN: Code duplicated, block: B:1057:0x228a  */
    /* JADX WARN: Code duplicated, block: B:1060:0x22a4  */
    /* JADX WARN: Code duplicated, block: B:1062:0x22b4  */
    /* JADX WARN: Code duplicated, block: B:1064:0x22c7  */
    /* JADX WARN: Code duplicated, block: B:1067:0x22dc  */
    /* JADX WARN: Code duplicated, block: B:1069:0x232c  */
    /* JADX WARN: Code duplicated, block: B:1070:0x232f  */
    /* JADX WARN: Code duplicated, block: B:1073:0x233a  */
    /* JADX WARN: Code duplicated, block: B:1074:0x233e  */
    /* JADX WARN: Code duplicated, block: B:1079:0x2398  */
    /* JADX WARN: Code duplicated, block: B:1084:0x23b6  */
    /* JADX WARN: Code duplicated, block: B:1087:0x23ce  */
    /* JADX WARN: Code duplicated, block: B:1094:0x241d  */
    /* JADX WARN: Code duplicated, block: B:1097:0x247d  */
    /* JADX WARN: Code duplicated, block: B:1100:0x2486  */
    /* JADX WARN: Code duplicated, block: B:1103:0x24d3  */
    /* JADX WARN: Code duplicated, block: B:1106:0x2502  */
    /* JADX WARN: Code duplicated, block: B:1108:0x2516  */
    /* JADX WARN: Code duplicated, block: B:1115:0x2536  */
    /* JADX WARN: Code duplicated, block: B:1117:0x254a  */
    /* JADX WARN: Code duplicated, block: B:1120:0x2562  */
    /* JADX WARN: Code duplicated, block: B:1122:0x2572  */
    /* JADX WARN: Code duplicated, block: B:1124:0x2587  */
    /* JADX WARN: Code duplicated, block: B:1127:0x259c  */
    /* JADX WARN: Code duplicated, block: B:1129:0x25b4  */
    /* JADX WARN: Code duplicated, block: B:1130:0x25c4  */
    /* JADX WARN: Code duplicated, block: B:1137:0x25f0  */
    /* JADX WARN: Code duplicated, block: B:1142:0x2613  */
    /* JADX WARN: Code duplicated, block: B:1145:0x2628  */
    /* JADX WARN: Code duplicated, block: B:1147:0x264b  */
    /* JADX WARN: Code duplicated, block: B:1150:0x266b  */
    /* JADX WARN: Code duplicated, block: B:1152:0x267f  */
    /* JADX WARN: Code duplicated, block: B:1153:0x268d  */
    /* JADX WARN: Code duplicated, block: B:1160:0x26b9  */
    /* JADX WARN: Code duplicated, block: B:1162:0x26e5  */
    /* JADX WARN: Code duplicated, block: B:1165:0x26fb  */
    /* JADX WARN: Code duplicated, block: B:1175:0x2739  */
    /* JADX WARN: Code duplicated, block: B:1177:0x2754  */
    /* JADX WARN: Code duplicated, block: B:1180:0x2769  */
    /* JADX WARN: Code duplicated, block: B:1182:0x277d  */
    /* JADX WARN: Code duplicated, block: B:1183:0x2789  */
    /* JADX WARN: Code duplicated, block: B:1188:0x27a1  */
    /* JADX WARN: Code duplicated, block: B:1190:0x27be  */
    /* JADX WARN: Code duplicated, block: B:1193:0x27d4  */
    /* JADX WARN: Code duplicated, block: B:1195:0x27e8  */
    /* JADX WARN: Code duplicated, block: B:1200:0x280a  */
    /* JADX WARN: Code duplicated, block: B:1203:0x2832  */
    /* JADX WARN: Code duplicated, block: B:1204:0x2834  */
    /* JADX WARN: Code duplicated, block: B:1207:0x2842  */
    /* JADX WARN: Code duplicated, block: B:1214:0x2855  */
    /* JADX WARN: Code duplicated, block: B:1216:0x289c  */
    /* JADX WARN: Code duplicated, block: B:1219:0x28b3  */
    /* JADX WARN: Code duplicated, block: B:1221:0x28c7  */
    /* JADX WARN: Code duplicated, block: B:1226:0x28e7  */
    /* JADX WARN: Code duplicated, block: B:1228:0x2903  */
    /* JADX WARN: Code duplicated, block: B:1231:0x2918  */
    /* JADX WARN: Code duplicated, block: B:1233:0x2928  */
    /* JADX WARN: Code duplicated, block: B:1235:0x2965  */
    /* JADX WARN: Code duplicated, block: B:1238:0x297a  */
    /* JADX WARN: Code duplicated, block: B:1240:0x298a  */
    /* JADX WARN: Code duplicated, block: B:1241:0x2997  */
    /* JADX WARN: Code duplicated, block: B:1243:0x29da  */
    /* JADX WARN: Code duplicated, block: B:1246:0x29ed  */
    /* JADX WARN: Code duplicated, block: B:1248:0x29fd  */
    /* JADX WARN: Code duplicated, block: B:1249:0x2a0a  */
    /* JADX WARN: Code duplicated, block: B:1251:0x2a52  */
    /* JADX WARN: Code duplicated, block: B:1254:0x2a61  */
    /* JADX WARN: Code duplicated, block: B:1256:0x2a71  */
    /* JADX WARN: Code duplicated, block: B:1257:0x2a7e  */
    /* JADX WARN: Code duplicated, block: B:1259:0x2ac8  */
    /* JADX WARN: Code duplicated, block: B:1261:0x2ad5  */
    /* JADX WARN: Code duplicated, block: B:1264:0x2ae4  */
    /* JADX WARN: Code duplicated, block: B:1265:0x2ae8  */
    /* JADX WARN: Code duplicated, block: B:1274:0x0e0e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:1276:0x15d6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:1278:0x15b5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:1281:0x1585 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:1283:0x1568 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:267:0x073a  */
    /* JADX WARN: Code duplicated, block: B:268:0x0745  */
    /* JADX WARN: Code duplicated, block: B:271:0x0754  */
    /* JADX WARN: Code duplicated, block: B:274:0x0769  */
    /* JADX WARN: Code duplicated, block: B:277:0x0780  */
    /* JADX WARN: Code duplicated, block: B:280:0x0797  */
    /* JADX WARN: Code duplicated, block: B:283:0x07ae  */
    /* JADX WARN: Code duplicated, block: B:286:0x07c5  */
    /* JADX WARN: Code duplicated, block: B:289:0x07dc  */
    /* JADX WARN: Code duplicated, block: B:292:0x07f3  */
    /* JADX WARN: Code duplicated, block: B:295:0x080a  */
    /* JADX WARN: Code duplicated, block: B:298:0x0821  */
    /* JADX WARN: Code duplicated, block: B:301:0x0838  */
    /* JADX WARN: Code duplicated, block: B:304:0x084f  */
    /* JADX WARN: Code duplicated, block: B:307:0x0866  */
    /* JADX WARN: Code duplicated, block: B:310:0x087d  */
    /* JADX WARN: Code duplicated, block: B:313:0x0894  */
    /* JADX WARN: Code duplicated, block: B:316:0x08ab  */
    /* JADX WARN: Code duplicated, block: B:319:0x08c2  */
    /* JADX WARN: Code duplicated, block: B:322:0x08fe  */
    /* JADX WARN: Code duplicated, block: B:325:0x0915  */
    /* JADX WARN: Code duplicated, block: B:326:0x0923  */
    /* JADX WARN: Code duplicated, block: B:329:0x0937  */
    /* JADX WARN: Code duplicated, block: B:330:0x0947  */
    /* JADX WARN: Code duplicated, block: B:333:0x0958  */
    /* JADX WARN: Code duplicated, block: B:336:0x096d  */
    /* JADX WARN: Code duplicated, block: B:339:0x0982  */
    /* JADX WARN: Code duplicated, block: B:342:0x09b1  */
    /* JADX WARN: Code duplicated, block: B:345:0x09c7  */
    /* JADX WARN: Code duplicated, block: B:348:0x09f9  */
    /* JADX WARN: Code duplicated, block: B:351:0x0a0f  */
    /* JADX WARN: Code duplicated, block: B:354:0x0a25  */
    /* JADX WARN: Code duplicated, block: B:357:0x0a58  */
    /* JADX WARN: Code duplicated, block: B:364:0x0a89  */
    /* JADX WARN: Code duplicated, block: B:371:0x0abc  */
    /* JADX WARN: Code duplicated, block: B:374:0x0afd  */
    /* JADX WARN: Code duplicated, block: B:375:0x0aff  */
    /* JADX WARN: Code duplicated, block: B:380:0x0b0d  */
    /* JADX WARN: Code duplicated, block: B:385:0x0b45  */
    /* JADX WARN: Code duplicated, block: B:386:0x0b47  */
    /* JADX WARN: Code duplicated, block: B:391:0x0b59  */
    /* JADX WARN: Code duplicated, block: B:394:0x0b7b  */
    /* JADX WARN: Code duplicated, block: B:395:0x0b88  */
    /* JADX WARN: Code duplicated, block: B:400:0x0bb1  */
    /* JADX WARN: Code duplicated, block: B:405:0x0c16  */
    /* JADX WARN: Code duplicated, block: B:407:0x0c1a  */
    /* JADX WARN: Code duplicated, block: B:412:0x0c3e  */
    /* JADX WARN: Code duplicated, block: B:417:0x0c98  */
    /* JADX WARN: Code duplicated, block: B:418:0x0c9a  */
    /* JADX WARN: Code duplicated, block: B:423:0x0cbf  */
    /* JADX WARN: Code duplicated, block: B:428:0x0cf4  */
    /* JADX WARN: Code duplicated, block: B:429:0x0cf6  */
    /* JADX WARN: Code duplicated, block: B:436:0x0d0a  */
    /* JADX WARN: Code duplicated, block: B:439:0x0d29  */
    /* JADX WARN: Code duplicated, block: B:440:0x0d2f  */
    /* JADX WARN: Code duplicated, block: B:445:0x0d55  */
    /* JADX WARN: Code duplicated, block: B:452:0x0d9a  */
    /* JADX WARN: Code duplicated, block: B:455:0x0dab  */
    /* JADX WARN: Code duplicated, block: B:457:0x0dbd  */
    /* JADX WARN: Code duplicated, block: B:458:0x0dc0  */
    /* JADX WARN: Code duplicated, block: B:462:0x0df7  */
    /* JADX WARN: Code duplicated, block: B:464:0x0e0b  */
    /* JADX WARN: Code duplicated, block: B:467:0x0e54  */
    /* JADX WARN: Code duplicated, block: B:469:0x0e57  */
    /* JADX WARN: Code duplicated, block: B:481:0x0ea3  */
    /* JADX WARN: Code duplicated, block: B:484:0x0eb0  */
    /* JADX WARN: Code duplicated, block: B:485:0x0ef6  */
    /* JADX WARN: Code duplicated, block: B:488:0x0f41  */
    /* JADX WARN: Code duplicated, block: B:489:0x0f43  */
    /* JADX WARN: Code duplicated, block: B:494:0x0f55  */
    /* JADX WARN: Code duplicated, block: B:497:0x0f72  */
    /* JADX WARN: Code duplicated, block: B:498:0x0f74  */
    /* JADX WARN: Code duplicated, block: B:503:0x0f81  */
    /* JADX WARN: Code duplicated, block: B:506:0x0f94  */
    /* JADX WARN: Code duplicated, block: B:507:0x0f96  */
    /* JADX WARN: Code duplicated, block: B:512:0x0fad  */
    /* JADX WARN: Code duplicated, block: B:515:0x0fea  */
    /* JADX WARN: Code duplicated, block: B:518:0x0ff6  */
    /* JADX WARN: Code duplicated, block: B:519:0x0ffa  */
    /* JADX WARN: Code duplicated, block: B:522:0x1055  */
    /* JADX WARN: Code duplicated, block: B:525:0x1061  */
    /* JADX WARN: Code duplicated, block: B:526:0x1065  */
    /* JADX WARN: Code duplicated, block: B:529:0x108c  */
    /* JADX WARN: Code duplicated, block: B:531:0x1091  */
    /* JADX WARN: Code duplicated, block: B:533:0x1094  */
    /* JADX WARN: Code duplicated, block: B:535:0x109c  */
    /* JADX WARN: Code duplicated, block: B:537:0x10ac  */
    /* JADX WARN: Code duplicated, block: B:540:0x10c8  */
    /* JADX WARN: Code duplicated, block: B:541:0x10ca  */
    /* JADX WARN: Code duplicated, block: B:544:0x10d2  */
    /* JADX WARN: Code duplicated, block: B:545:0x10d4  */
    /* JADX WARN: Code duplicated, block: B:552:0x10ec  */
    /* JADX WARN: Code duplicated, block: B:554:0x1141  */
    /* JADX WARN: Code duplicated, block: B:556:0x115d  */
    /* JADX WARN: Code duplicated, block: B:557:0x115f  */
    /* JADX WARN: Code duplicated, block: B:562:0x116d  */
    /* JADX WARN: Code duplicated, block: B:566:0x119b  */
    /* JADX WARN: Code duplicated, block: B:568:0x11a8  */
    /* JADX WARN: Code duplicated, block: B:572:0x11e6  */
    /* JADX WARN: Code duplicated, block: B:575:0x11fe  */
    /* JADX WARN: Code duplicated, block: B:578:0x1216  */
    /* JADX WARN: Code duplicated, block: B:579:0x1218  */
    /* JADX WARN: Code duplicated, block: B:584:0x1226  */
    /* JADX WARN: Code duplicated, block: B:586:0x124f  */
    /* JADX WARN: Code duplicated, block: B:588:0x1271  */
    /* JADX WARN: Code duplicated, block: B:590:0x12a5  */
    /* JADX WARN: Code duplicated, block: B:593:0x12b1  */
    /* JADX WARN: Code duplicated, block: B:594:0x12b5  */
    /* JADX WARN: Code duplicated, block: B:596:0x1303  */
    /* JADX WARN: Code duplicated, block: B:598:0x1309  */
    /* JADX WARN: Code duplicated, block: B:600:0x1319  */
    /* JADX WARN: Code duplicated, block: B:601:0x1327  */
    /* JADX WARN: Code duplicated, block: B:604:0x135f  */
    /* JADX WARN: Code duplicated, block: B:607:0x136b  */
    /* JADX WARN: Code duplicated, block: B:608:0x136f  */
    /* JADX WARN: Code duplicated, block: B:611:0x13af  */
    /* JADX WARN: Code duplicated, block: B:614:0x13bb  */
    /* JADX WARN: Code duplicated, block: B:615:0x13bf  */
    /* JADX WARN: Code duplicated, block: B:620:0x142e  */
    /* JADX WARN: Code duplicated, block: B:623:0x1443  */
    /* JADX WARN: Code duplicated, block: B:626:0x1459  */
    /* JADX WARN: Code duplicated, block: B:627:0x147e  */
    /* JADX WARN: Code duplicated, block: B:629:0x14c4  */
    /* JADX WARN: Code duplicated, block: B:631:0x14d1  */
    /* JADX WARN: Code duplicated, block: B:633:0x1503  */
    /* JADX WARN: Code duplicated, block: B:634:0x1505  */
    /* JADX WARN: Code duplicated, block: B:641:0x1527  */
    /* JADX WARN: Code duplicated, block: B:646:0x155f  */
    /* JADX WARN: Code duplicated, block: B:649:0x156e  */
    /* JADX WARN: Code duplicated, block: B:657:0x15ac  */
    /* JADX WARN: Code duplicated, block: B:660:0x15bb  */
    /* JADX WARN: Code duplicated, block: B:666:0x1603  */
    /* JADX WARN: Code duplicated, block: B:667:0x1612  */
    /* JADX WARN: Code duplicated, block: B:673:0x1634  */
    /* JADX WARN: Code duplicated, block: B:677:0x1673  */
    /* JADX WARN: Code duplicated, block: B:678:0x1675  */
    /* JADX WARN: Code duplicated, block: B:685:0x1689  */
    /* JADX WARN: Code duplicated, block: B:688:0x169f  */
    /* JADX WARN: Code duplicated, block: B:689:0x16a1  */
    /* JADX WARN: Code duplicated, block: B:696:0x16b4  */
    /* JADX WARN: Code duplicated, block: B:699:0x16c8  */
    /* JADX WARN: Code duplicated, block: B:700:0x16ca  */
    /* JADX WARN: Code duplicated, block: B:705:0x16da  */
    /* JADX WARN: Code duplicated, block: B:708:0x16f1  */
    /* JADX WARN: Code duplicated, block: B:709:0x16f3  */
    /* JADX WARN: Code duplicated, block: B:714:0x1703  */
    /* JADX WARN: Code duplicated, block: B:717:0x171a  */
    /* JADX WARN: Code duplicated, block: B:718:0x171c  */
    /* JADX WARN: Code duplicated, block: B:721:0x1722  */
    /* JADX WARN: Code duplicated, block: B:722:0x1724  */
    /* JADX WARN: Code duplicated, block: B:729:0x1739  */
    /* JADX WARN: Code duplicated, block: B:734:0x175f  */
    /* JADX WARN: Code duplicated, block: B:737:0x1776  */
    /* JADX WARN: Code duplicated, block: B:738:0x1783  */
    /* JADX WARN: Code duplicated, block: B:741:0x1793  */
    /* JADX WARN: Code duplicated, block: B:742:0x179d  */
    /* JADX WARN: Code duplicated, block: B:745:0x17ac  */
    /* JADX WARN: Code duplicated, block: B:746:0x17b9  */
    /* JADX WARN: Code duplicated, block: B:756:0x1807  */
    /* JADX WARN: Code duplicated, block: B:767:0x1831  */
    /* JADX WARN: Code duplicated, block: B:774:0x184a  */
    /* JADX WARN: Code duplicated, block: B:776:0x184e  */
    /* JADX WARN: Code duplicated, block: B:778:0x1864  */
    /* JADX WARN: Code duplicated, block: B:781:0x18a3  */
    /* JADX WARN: Code duplicated, block: B:782:0x18b0  */
    /* JADX WARN: Code duplicated, block: B:785:0x18c0  */
    /* JADX WARN: Code duplicated, block: B:786:0x18d3  */
    /* JADX WARN: Code duplicated, block: B:789:0x18eb  */
    /* JADX WARN: Code duplicated, block: B:790:0x18fd  */
    /* JADX WARN: Code duplicated, block: B:793:0x1913  */
    /* JADX WARN: Code duplicated, block: B:798:0x1932  */
    /* JADX WARN: Code duplicated, block: B:804:0x195e  */
    /* JADX WARN: Code duplicated, block: B:807:0x196c  */
    /* JADX WARN: Code duplicated, block: B:809:0x197d  */
    /* JADX WARN: Code duplicated, block: B:810:0x1980  */
    /* JADX WARN: Code duplicated, block: B:813:0x19a4  */
    /* JADX WARN: Code duplicated, block: B:814:0x19af  */
    /* JADX WARN: Code duplicated, block: B:817:0x19bf  */
    /* JADX WARN: Code duplicated, block: B:818:0x19ca  */
    /* JADX WARN: Code duplicated, block: B:821:0x19da  */
    /* JADX WARN: Code duplicated, block: B:823:0x1a14  */
    /* JADX WARN: Code duplicated, block: B:826:0x1a34  */
    /* JADX WARN: Code duplicated, block: B:828:0x1a46  */
    /* JADX WARN: Code duplicated, block: B:830:0x1a58  */
    /* JADX WARN: Code duplicated, block: B:831:0x1a65  */
    /* JADX WARN: Code duplicated, block: B:836:0x1a7d  */
    /* JADX WARN: Code duplicated, block: B:839:0x1a8e  */
    /* JADX WARN: Code duplicated, block: B:840:0x1a90  */
    /* JADX WARN: Code duplicated, block: B:847:0x1aa2  */
    /* JADX WARN: Code duplicated, block: B:849:0x1adf  */
    /* JADX WARN: Code duplicated, block: B:852:0x1af8  */
    /* JADX WARN: Code duplicated, block: B:854:0x1b08  */
    /* JADX WARN: Code duplicated, block: B:860:0x1b3f  */
    /* JADX WARN: Code duplicated, block: B:863:0x1b56  */
    /* JADX WARN: Code duplicated, block: B:864:0x1b65  */
    /* JADX WARN: Code duplicated, block: B:866:0x1b7e  */
    /* JADX WARN: Code duplicated, block: B:868:0x1b91  */
    /* JADX WARN: Code duplicated, block: B:871:0x1bac  */
    /* JADX WARN: Code duplicated, block: B:875:0x1bc8  */
    /* JADX WARN: Code duplicated, block: B:880:0x1be9  */
    /* JADX WARN: Code duplicated, block: B:883:0x1c02  */
    /* JADX WARN: Code duplicated, block: B:884:0x1c13  */
    /* JADX WARN: Code duplicated, block: B:886:0x1c32  */
    /* JADX WARN: Code duplicated, block: B:889:0x1c51  */
    /* JADX WARN: Code duplicated, block: B:891:0x1c63  */
    /* JADX WARN: Code duplicated, block: B:892:0x1c72  */
    /* JADX WARN: Code duplicated, block: B:894:0x1cc8  */
    /* JADX WARN: Code duplicated, block: B:897:0x1cea  */
    /* JADX WARN: Code duplicated, block: B:899:0x1d08  */
    /* JADX WARN: Code duplicated, block: B:900:0x1d15  */
    /* JADX WARN: Code duplicated, block: B:903:0x1d27  */
    /* JADX WARN: Code duplicated, block: B:910:0x1d52  */
    /* JADX WARN: Code duplicated, block: B:913:0x1d7d  */
    /* JADX WARN: Code duplicated, block: B:914:0x1d88  */
    /* JADX WARN: Code duplicated, block: B:916:0x1da2  */
    /* JADX WARN: Code duplicated, block: B:919:0x1db9  */
    /* JADX WARN: Code duplicated, block: B:925:0x1de5  */
    /* JADX WARN: Code duplicated, block: B:928:0x1e0d  */
    /* JADX WARN: Code duplicated, block: B:930:0x1e2d  */
    /* JADX WARN: Code duplicated, block: B:933:0x1e46  */
    /* JADX WARN: Code duplicated, block: B:935:0x1e52  */
    /* JADX WARN: Code duplicated, block: B:936:0x1e54  */
    /* JADX WARN: Code duplicated, block: B:939:0x1e5c  */
    /* JADX WARN: Code duplicated, block: B:940:0x1e5e  */
    /* JADX WARN: Code duplicated, block: B:943:0x1e7b  */
    /* JADX WARN: Code duplicated, block: B:944:0x1e7d  */
    /* JADX WARN: Code duplicated, block: B:949:0x1e8e  */
    /* JADX WARN: Code duplicated, block: B:954:0x1eda  */
    /* JADX WARN: Code duplicated, block: B:956:0x1eff  */
    /* JADX WARN: Code duplicated, block: B:959:0x1f25  */
    /* JADX WARN: Code duplicated, block: B:961:0x1f37  */
    /* JADX WARN: Code duplicated, block: B:962:0x1f42  */
    /* JADX WARN: Code duplicated, block: B:965:0x1f52  */
    /* JADX WARN: Code duplicated, block: B:966:0x1f5f  */
    /* JADX WARN: Code duplicated, block: B:969:0x1f6f  */
    /* JADX WARN: Code duplicated, block: B:970:0x1f7c  */
    /* JADX WARN: Code duplicated, block: B:973:0x1f8c  */
    /* JADX WARN: Code duplicated, block: B:974:0x1f97  */
    /* JADX WARN: Code duplicated, block: B:977:0x1fa7  */
    /* JADX WARN: Code duplicated, block: B:978:0x1fb4  */
    /* JADX WARN: Code duplicated, block: B:981:0x1fc4  */
    /* JADX WARN: Code duplicated, block: B:982:0x1fd1  */
    /* JADX WARN: Code duplicated, block: B:985:0x1fe2  */
    /* JADX WARN: Code duplicated, block: B:986:0x1ff1  */
    /* JADX WARN: Code duplicated, block: B:989:0x2003  */
    /* JADX WARN: Code duplicated, block: B:990:0x2012  */
    /* JADX WARN: Code duplicated, block: B:995:0x202f  */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void f(Function1 function1, final Function1 function2, Function1 function3, Function2 function4, Function2 function5, Function1 function6, Function0 function0, Function0 function7, Function0 function8, Function0 function9, ir0 ir0Var, fs0 fs0Var, boolean z, Composer composer, int i, int i2) {
        Composer composer2;
        int i3;
        SnapshotMutationPolicy snapshotMutationPolicy;
        int i4;
        CoroutineContext coroutineContext;
        int i5;
        int i6;
        int i7;
        SnapshotMutationPolicy snapshotMutationPolicy2;
        int i8;
        ServerPreferences serverPreferences;
        SnapshotMutationPolicy snapshotMutationPolicy3;
        Object j6Var;
        Object objRememberedValue;
        int i9;
        final MutableState mutableState;
        Object objRememberedValue2;
        final MutableState mutableState2;
        Object objRememberedValue3;
        final MutableState mutableState3;
        Object objRememberedValue4;
        final MutableState mutableState4;
        Object objRememberedValue5;
        final MutableState mutableState5;
        Object objRememberedValue6;
        final MutableState mutableState6;
        Object objRememberedValue7;
        final MutableState mutableState7;
        Object objRememberedValue8;
        final MutableState mutableState8;
        Object objRememberedValue9;
        final MutableState mutableState9;
        Object objRememberedValue10;
        final MutableState mutableState10;
        Object objRememberedValue11;
        final MutableState mutableState11;
        Object objRememberedValue12;
        final MutableState mutableState12;
        Object objRememberedValue13;
        final MutableState mutableState13;
        Object objRememberedValue14;
        final MutableState mutableState14;
        Object objRememberedValue15;
        final MutableState mutableState15;
        Object objRememberedValue16;
        final MutableState mutableState16;
        Object objRememberedValue17;
        final MutableState mutableState17;
        Object objRememberedValue18;
        v52 v52Var;
        ServerPreferences serverPreferences2;
        State stateCollectAsState;
        State stateCollectAsState2;
        Object objRememberedValue19;
        SubtitleFontUploadServerManager subtitleFontUploadServerManager;
        Object objRememberedValue20;
        Object obj;
        SnapshotMutationPolicy snapshotMutationPolicy4;
        MutableState mutableState18;
        Object objRememberedValue21;
        int i10;
        MutableState mutableState19;
        Object objRememberedValue22;
        final MutableState mutableState20;
        Object objRememberedValue23;
        final MutableState mutableState21;
        Object objRememberedValue24;
        cc1 cc1Var;
        State stateCollectAsState3;
        Object objRememberedValue25;
        OnlineSubtitleConfigServerManager onlineSubtitleConfigServerManager;
        Object objRememberedValue26;
        oh2 oh2Var;
        State stateCollectAsState4;
        Object objRememberedValue27;
        nh2 nh2Var;
        Object objRememberedValue28;
        WebDavSyncConfigServerManager webDavSyncConfigServerManager;
        Object objRememberedValue29;
        State stateCollectAsState5;
        Object objRememberedValue30;
        vc2 vc2Var;
        sc2 sc2Var;
        bd bdVarB;
        boolean zChanged;
        Object objRememberedValue31;
        boolean zChangedInstance;
        Object objRememberedValue32;
        v52 v52Var2;
        int i11;
        boolean z2;
        boolean z3;
        Object objRememberedValue33;
        Composer composer3;
        MutableState mutableState22;
        fs0 fs0Var2;
        Context context;
        boolean z4;
        State state;
        boolean z5;
        boolean zChangedInstance2;
        Object objRememberedValue34;
        int i12;
        Object objRememberedValue35;
        int i13;
        to0 to0VarL;
        boolean zChanged2;
        to0 to0Var;
        Object j6Var2;
        final MutableState mutableState23;
        State state2;
        boolean z6;
        boolean zChanged3;
        Object nq0Var;
        MutableState mutableState24;
        boolean z7;
        boolean zChanged4;
        Object oq0Var;
        final boolean z8;
        State state3;
        boolean z9;
        Object objRememberedValue36;
        final MutableState mutableState25;
        Continuation continuation;
        String strC;
        ServerConfig serverById;
        boolean zChangedInstance3;
        Object objRememberedValue37;
        ServerPreferences serverPreferences3;
        final fs0 fs0Var3;
        boolean zChangedInstance4;
        Object objRememberedValue38;
        List listEmptyList;
        List list;
        List listA;
        boolean z10;
        Object objRememberedValue39;
        Composer.Companion companion;
        MutableState mutableState26;
        MutableState mutableState27;
        bd bdVar;
        MutableState mutableState28;
        boolean z11;
        boolean zChangedInstance5;
        Object objRememberedValue40;
        Modifier modifier;
        State state4;
        boolean z12;
        Object objRememberedValue41;
        boolean z13;
        boolean zChanged5;
        Object objRememberedValue42;
        Alignment.Companion companion2;
        ComposeUiNode.Companion companion3;
        Function0 constructor;
        BoxScopeInstance boxScopeInstance;
        Arrangement arrangement;
        Function0 constructor2;
        int iOrdinal;
        ServerPreferences serverPreferences4;
        MutableState mutableState29;
        State state5;
        int i14;
        nr0 nr0Var;
        bd bdVar2;
        String str;
        mr0 mr0Var;
        List list2;
        List list3;
        boolean z14;
        boolean zChanged6;
        Object objRememberedValue43;
        mr0 mr0Var2;
        final MutableState mutableState30;
        boolean zChanged7;
        Object objRememberedValue44;
        ArrayList arrayList;
        Object obj2;
        boolean zChanged8;
        Object objRememberedValue45;
        ArrayList arrayList2;
        Object obj3;
        boolean zChangedInstance6;
        Object objRememberedValue46;
        Function3 function10;
        int i15;
        boolean z15;
        boolean z16;
        Object objRememberedValue47;
        final Function1 function11;
        boolean z17;
        boolean z18;
        Object objRememberedValue48;
        final int i16;
        int i17;
        int i18;
        Object objRememberedValue49;
        int i19;
        int i20;
        int i21;
        Object objRememberedValue50;
        boolean z19;
        boolean z20;
        boolean z21;
        Object objRememberedValue51;
        MutableState mutableState31;
        boolean z22;
        boolean zChangedInstance7;
        Object objRememberedValue52;
        Object objRememberedValue53;
        Composer.Companion companion4;
        State state6;
        Object objRememberedValue54;
        Object objRememberedValue55;
        Object objRememberedValue56;
        Object obj4;
        MutableState mutableState32;
        Function0 constructor3;
        Function0 constructor4;
        MaterialTheme materialTheme;
        int i22;
        boolean zChangedInstance8;
        Object objRememberedValue57;
        Object objRememberedValue58;
        Modifier modifier2;
        Function0 constructor5;
        String strC2;
        ServerPreferences serverPreferences5;
        String host;
        boolean z23;
        boolean z24;
        MutableState mutableState33;
        MutableState mutableState34;
        MutableState mutableState35;
        MutableState mutableState36;
        Context context2;
        MutableState mutableState37;
        MutableState mutableState38;
        CoroutineScope coroutineScope;
        MutableState mutableState39;
        MutableState mutableState40;
        Composer composer4;
        int i23;
        MutableState mutableState41;
        MutableState mutableState42;
        Composer composer5;
        MutableState mutableState43;
        Composer composer6;
        int i24;
        MutableState mutableState44;
        bd bdVar3;
        Composer composer7;
        Context context3;
        MutableState mutableState45;
        MutableState mutableState46;
        MutableState mutableState47;
        MutableState mutableState48;
        MutableState mutableState49;
        MutableState mutableState50;
        MutableState mutableState51;
        MutableState mutableState52;
        MutableState mutableState53;
        MutableState mutableState54;
        MutableState mutableState55;
        Object objRememberedValue59;
        Composer.Companion companion5;
        Composer composer8;
        int i25;
        final UserPreferences userPreferences;
        State state7;
        Composer composer9;
        MutableState mutableState56;
        MutableState mutableState57;
        MutableState mutableState58;
        MutableState mutableState59;
        MutableState mutableState60;
        MutableState mutableState61;
        MutableState mutableState62;
        MutableState mutableState63;
        MutableState mutableState64;
        MutableState mutableState65;
        MutableState mutableState66;
        MutableState mutableState67;
        boolean z25;
        Composer composer10;
        boolean z26;
        MutableState mutableState68;
        MutableState mutableState69;
        CoroutineScope coroutineScope2;
        Composer composer11;
        Composer composer12;
        int i26;
        int i27;
        Object objRememberedValue60;
        MutableState mutableState70;
        Object objRememberedValue61;
        MutableState mutableState71;
        Object objRememberedValue62;
        MutableState mutableState72;
        Object objRememberedValue63;
        Object objRememberedValue64;
        vc2 vc2Var2;
        boolean zChangedInstance9;
        Object objRememberedValue65;
        Object objRememberedValue66;
        boolean zChangedInstance10;
        Object objRememberedValue67;
        ServerPreferences serverPreferences6;
        boolean z27;
        boolean z28;
        Object objRememberedValue68;
        Object objRememberedValue69;
        MutableState mutableState73;
        boolean zChangedInstance11;
        Object objRememberedValue70;
        Object objRememberedValue71;
        Object objRememberedValue72;
        MutableState mutableState74;
        MutableState mutableState75;
        boolean zChangedInstance12;
        Object j4Var;
        Object objRememberedValue73;
        v52 v52Var3;
        boolean zChangedInstance13;
        Object objRememberedValue74;
        boolean zChangedInstance14;
        Object objRememberedValue75;
        Object objRememberedValue76;
        Object objRememberedValue77;
        Object objRememberedValue78;
        boolean zChangedInstance15;
        Object objRememberedValue79;
        SharedPreferences sharedPreferences;
        boolean z29;
        boolean z30;
        SharedPreferences sharedPreferences2;
        boolean z31;
        boolean zChangedInstance16;
        Object objRememberedValue80;
        boolean zChangedInstance17;
        Object objRememberedValue81;
        Object objRememberedValue82;
        Function0 function12;
        boolean zChangedInstance18;
        final MutableState mutableState76;
        Object obj5;
        MutableState mutableState77;
        MutableState mutableState78;
        boolean zChangedInstance19;
        Object objRememberedValue83;
        Function0 function13;
        Object objRememberedValue84;
        Object objRememberedValue85;
        boolean zChangedInstance20;
        Object objRememberedValue86;
        Object objRememberedValue87;
        Object objRememberedValue88;
        Object objRememberedValue89;
        Composer.Companion companion6;
        Object objRememberedValue90;
        Object objRememberedValue91;
        Object objRememberedValue92;
        MutableState mutableState79;
        Object objRememberedValue93;
        Object objRememberedValue94;
        MutableState mutableState80;
        Object objRememberedValue95;
        Object objRememberedValue96;
        boolean zChanged9;
        Object objRememberedValue97;
        bd bdVar4;
        Object objRememberedValue98;
        MutableState mutableState81;
        Object objRememberedValue99;
        Object objRememberedValue100;
        Object objRememberedValue101;
        Object objRememberedValue102;
        boolean zChangedInstance21;
        Composer composer13;
        Object objRememberedValue103;
        Composer composer14;
        boolean z32;
        boolean z33;
        boolean z34;
        boolean z35;
        Object objRememberedValue104;
        int i28;
        MutableState mutableState82;
        Object objRememberedValue105;
        boolean zChanged10;
        Object objRememberedValue106;
        MutableState mutableState83;
        Object objRememberedValue107;
        Object objRememberedValue108;
        Composer.Companion companion7;
        Object objRememberedValue109;
        boolean zChangedInstance22;
        Object objRememberedValue110;
        Object objRememberedValue111;
        Object objRememberedValue112;
        MutableState mutableState84;
        boolean zChangedInstance23;
        Object objRememberedValue113;
        boolean zChangedInstance24;
        Object objRememberedValue114;
        Object objRememberedValue115;
        nr0 nr0Var2;
        boolean zChangedInstance25;
        Object objRememberedValue116;
        Object objRememberedValue117;
        Object objRememberedValue118;
        Composer.Companion companion8;
        MutableState mutableState85;
        Context context4;
        boolean zChangedInstance26;
        Object objRememberedValue119;
        boolean z36;
        Object objRememberedValue120;
        Object objRememberedValue121;
        Composer.Companion companion9;
        Object objRememberedValue122;
        Object objRememberedValue123;
        Object objRememberedValue124;
        Object objRememberedValue125;
        boolean zChangedInstance27;
        Object objRememberedValue126;
        boolean z37;
        boolean z38;
        Object objRememberedValue127;
        Object objRememberedValue128;
        Object objRememberedValue129;
        ServerConfig serverById2;
        ServerConfig effectiveServerConfig;
        ServerPreferences serverPreferences7;
        final MutableState mutableState86;
        boolean zChanged11;
        Object objRememberedValue130;
        Object objRememberedValue131;
        boolean z39;
        boolean z40;
        Object objRememberedValue132;
        boolean z41;
        boolean z42;
        Object objRememberedValue133;
        Object objRememberedValue134;
        boolean z43;
        boolean z44;
        boolean z45;
        Object objRememberedValue135;
        List listCreateListBuilder;
        boolean z46;
        Iterator it;
        String alias;
        function1.getClass();
        function2.getClass();
        Composer composerStartRestartGroup = composer.startRestartGroup(916822889);
        int i29 = (i & 6) == 0 ? (composerStartRestartGroup.changedInstance(function1) ? 4 : 2) | i : i;
        if ((i & 48) == 0) {
            i29 |= composerStartRestartGroup.changedInstance(function2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i29 |= composerStartRestartGroup.changedInstance(function3) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i29 |= composerStartRestartGroup.changedInstance(function4) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i29 |= composerStartRestartGroup.changedInstance(function5) ? GL20.GL_COLOR_BUFFER_BIT : 8192;
        }
        if ((i & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE) == 0) {
            i29 |= composerStartRestartGroup.changedInstance(function6) ? ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ : 65536;
        }
        if ((i & 1572864) == 0) {
            i29 |= composerStartRestartGroup.changedInstance(function0) ? ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES : 524288;
        }
        if ((i & 12582912) == 0) {
            i29 |= composerStartRestartGroup.changedInstance(function7) ? 8388608 : 4194304;
        }
        int i30 = (i2 & 6) == 0 ? i2 | (composerStartRestartGroup.changed(ir0Var) ? 4 : 2) : i2;
        if ((i2 & 48) == 0) {
            i30 |= composerStartRestartGroup.changedInstance(fs0Var) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i30 |= composerStartRestartGroup.changed(z) ? 256 : 128;
        }
        int i31 = i30;
        if (composerStartRestartGroup.shouldExecute(((i29 & 4793491) == 4793490 && (i31 & 147) == 146) ? false : true, i29 & 1)) {
            composerStartRestartGroup.startDefaults();
            if ((i & 1) != 0 && !composerStartRestartGroup.getDefaultsInvalid()) {
                composerStartRestartGroup.skipToGroupEnd();
            }
            composerStartRestartGroup.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(916822889, i29, i31, "com.dh.myembyapp.ui.screens.home.HomeScreen (HomeScreen.kt:194)");
            }
            Object objRememberedValue136 = composerStartRestartGroup.rememberedValue();
            Composer.Companion companion10 = Composer.Companion;
            if (objRememberedValue136 == companion10.getEmpty()) {
                objRememberedValue136 = SnapshotStateKt.mutableStateOf$default(to0.a, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue136);
            }
            MutableState mutableState87 = (MutableState) objRememberedValue136;
            Object[] objArr = new Object[0];
            Object objRememberedValue137 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue137 == companion10.getEmpty()) {
                objRememberedValue137 = new x10(26);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue137);
            }
            MutableState mutableState88 = (MutableState) RememberSaveableKt.rememberSaveable(objArr, (Function0) objRememberedValue137, composerStartRestartGroup, 48);
            State stateCollectAsState6 = SnapshotStateKt.collectAsState(fs0Var.s(), (CoroutineContext) null, composerStartRestartGroup, 0, 1);
            Object objRememberedValue138 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue138 == companion10.getEmpty()) {
                objRememberedValue138 = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue138);
            }
            final State state8 = (MutableState) objRememberedValue138;
            Object objRememberedValue139 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue139 == companion10.getEmpty()) {
                objRememberedValue139 = zr.n(composerStartRestartGroup);
            }
            FocusRequester focusRequester = (FocusRequester) objRememberedValue139;
            Object objRememberedValue140 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue140 == companion10.getEmpty()) {
                i3 = 2;
                snapshotMutationPolicy = null;
                objRememberedValue140 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue140);
            } else {
                i3 = 2;
                snapshotMutationPolicy = null;
            }
            final MutableState mutableState89 = (MutableState) objRememberedValue140;
            Object objRememberedValue141 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue141 == companion10.getEmpty()) {
                objRememberedValue141 = SnapshotStateKt.mutableStateOf$default(0, snapshotMutationPolicy, i3, snapshotMutationPolicy);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue141);
            }
            final MutableState mutableState90 = (MutableState) objRememberedValue141;
            Object objRememberedValue142 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue142 == companion10.getEmpty()) {
                objRememberedValue142 = SnapshotStateKt.mutableStateOf$default(0, snapshotMutationPolicy, i3, snapshotMutationPolicy);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue142);
            }
            final MutableState mutableState91 = (MutableState) objRememberedValue142;
            Object[] objArr2 = new Object[0];
            Object objRememberedValue143 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue143 == companion10.getEmpty()) {
                objRememberedValue143 = new x10(27);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue143);
            }
            MutableState mutableState92 = (MutableState) RememberSaveableKt.rememberSaveable(objArr2, (Function0) objRememberedValue143, composerStartRestartGroup, 48);
            Object objRememberedValue144 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue144 == companion10.getEmpty()) {
                i4 = 2;
                coroutineContext = null;
                objRememberedValue144 = SnapshotStateKt.mutableStateOf$default(0, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue144);
            } else {
                i4 = 2;
                coroutineContext = null;
            }
            final MutableState mutableState93 = (MutableState) objRememberedValue144;
            Object objRememberedValue145 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue145 == companion10.getEmpty()) {
                objRememberedValue145 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, coroutineContext, i4, coroutineContext);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue145);
            }
            MutableState mutableState94 = (MutableState) objRememberedValue145;
            State stateCollectAsState7 = SnapshotStateKt.collectAsState(fs0Var.z(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState8 = SnapshotStateKt.collectAsState(fs0Var.p(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState9 = SnapshotStateKt.collectAsState(fs0Var.q(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState10 = SnapshotStateKt.collectAsState(fs0Var.r(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState11 = SnapshotStateKt.collectAsState(fs0Var.v(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState12 = SnapshotStateKt.collectAsState(fs0Var.x(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState13 = SnapshotStateKt.collectAsState(fs0Var.t(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState14 = SnapshotStateKt.collectAsState(fs0Var.y(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState15 = SnapshotStateKt.collectAsState(fs0Var.o(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState16 = SnapshotStateKt.collectAsState(fs0Var.w(), coroutineContext, composerStartRestartGroup, 0, 1);
            State stateCollectAsState17 = SnapshotStateKt.collectAsState(fs0Var.u(), coroutineContext, composerStartRestartGroup, 0, 1);
            Context context5 = (Context) composerStartRestartGroup.consume(AndroidCompositionLocals_androidKt.getLocalContext());
            Object objRememberedValue146 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue146 == companion10.getEmpty()) {
                objRememberedValue146 = new UserPreferences(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue146);
            }
            UserPreferences userPreferences2 = (UserPreferences) objRememberedValue146;
            Object objRememberedValue147 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue147 == companion10.getEmpty()) {
                objRememberedValue147 = new ServerPreferences(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue147);
            }
            ServerPreferences serverPreferences8 = (ServerPreferences) objRememberedValue147;
            Object objRememberedValue148 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue148 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableAggregateSearch()), (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default);
                objRememberedValue148 = mutableStateMutableStateOf$default;
                i5 = 2;
            } else {
                i5 = 2;
            }
            final MutableState mutableState95 = (MutableState) objRememberedValue148;
            Object objRememberedValue149 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue149 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default2 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableAggregateSearchInServerSearch()), (SnapshotMutationPolicy) null, i5, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default2);
                objRememberedValue149 = mutableStateMutableStateOf$default2;
            }
            final MutableState mutableState96 = (MutableState) objRememberedValue149;
            boolean zBooleanValue = ((Boolean) mutableState96.getValue()).booleanValue();
            Object objRememberedValue150 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue150 == companion10.getEmpty()) {
                i6 = 2;
                objRememberedValue150 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getDisableTopNavFocusRefresh()), (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue150);
            } else {
                i6 = 2;
            }
            final MutableState mutableState97 = (MutableState) objRememberedValue150;
            Object objRememberedValue151 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue151 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default3 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableTransparentTopNavButtons()), (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default3);
                objRememberedValue151 = mutableStateMutableStateOf$default3;
            }
            final MutableState mutableState98 = (MutableState) objRememberedValue151;
            Object objRememberedValue152 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue152 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default4 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableClickableLibrarySectionTitle()), (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default4);
                objRememberedValue152 = mutableStateMutableStateOf$default4;
            }
            final MutableState mutableState99 = (MutableState) objRememberedValue152;
            Object objRememberedValue153 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue153 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default5 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableLibraryRowMoreButton()), (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default5);
                objRememberedValue153 = mutableStateMutableStateOf$default5;
            }
            final MutableState mutableState100 = (MutableState) objRememberedValue153;
            Object objRememberedValue154 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue154 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default6 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableHomeUnplayedOnly()), (SnapshotMutationPolicy) null, i6, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default6);
                objRememberedValue154 = mutableStateMutableStateOf$default6;
            }
            State state9 = (MutableState) objRememberedValue154;
            Object objRememberedValue155 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue155 == companion10.getEmpty()) {
                i7 = 2;
                objRememberedValue155 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnablePlaybackStartupPoster()), (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue155);
            } else {
                i7 = 2;
            }
            final MutableState mutableState101 = (MutableState) objRememberedValue155;
            Object objRememberedValue156 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue156 == companion10.getEmpty()) {
                objRememberedValue156 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableAggregateSearchVersionTags()), (SnapshotMutationPolicy) null, i7, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue156);
            }
            final MutableState mutableState102 = (MutableState) objRememberedValue156;
            Object objRememberedValue157 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue157 == companion10.getEmpty()) {
                objRememberedValue157 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableDetailAggregateVersionRail()), (SnapshotMutationPolicy) null, i7, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue157);
            }
            final MutableState mutableState103 = (MutableState) objRememberedValue157;
            Object objRememberedValue158 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue158 == companion10.getEmpty()) {
                objRememberedValue158 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableHomeBackFocusServerButton()), (SnapshotMutationPolicy) null, i7, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue158);
            }
            final MutableState mutableState104 = (MutableState) objRememberedValue158;
            Object objRememberedValue159 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue159 == companion10.getEmpty()) {
                objRememberedValue159 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableAppUpdateCheckOnStartup()), (SnapshotMutationPolicy) null, i7, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue159);
            }
            State state10 = (MutableState) objRememberedValue159;
            Object objRememberedValue160 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue160 == companion10.getEmpty()) {
                objRememberedValue160 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getAdminDebugFeaturesUnlocked()), (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue160);
            }
            State state11 = (MutableState) objRememberedValue160;
            Object objRememberedValue161 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue161 == companion10.getEmpty()) {
                objRememberedValue161 = SnapshotStateKt.mutableStateOf$default(Boolean.valueOf(userPreferences2.getEnableSpecialFeatures()), (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue161);
            }
            final MutableState mutableState105 = (MutableState) objRememberedValue161;
            boolean z47 = (((i31 & 14) ^ 6) > 4 && composerStartRestartGroup.changed(ir0Var)) || (i31 & 6) == 4;
            Object objRememberedValue162 = composerStartRestartGroup.rememberedValue();
            if (z47 || objRememberedValue162 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default7 = SnapshotStateKt.mutableStateOf$default(ir0Var, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default7);
                objRememberedValue162 = mutableStateMutableStateOf$default7;
            }
            MutableState mutableState106 = (MutableState) objRememberedValue162;
            Object[] objArr3 = {M(mutableState106).c()};
            Object objRememberedValue163 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue163 == companion10.getEmpty()) {
                objRememberedValue163 = new x10(28);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue163);
            }
            MutableState mutableState107 = (MutableState) RememberSaveableKt.rememberSaveable(objArr3, (Function0) objRememberedValue163, composerStartRestartGroup, 48);
            Object objRememberedValue164 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue164 == companion10.getEmpty()) {
                objRememberedValue164 = new ProxySettings(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue164);
            }
            ProxySettings proxySettings = (ProxySettings) objRememberedValue164;
            State stateCollectAsState18 = SnapshotStateKt.collectAsState(proxySettings.getProxyConfigFlow(), new ProxyConfig(false, null, null, 0, null, null, false, 127, null), (CoroutineContext) null, composerStartRestartGroup, 0, 2);
            Object objRememberedValue165 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue165 == companion10.getEmpty()) {
                objRememberedValue165 = new ProxyConfigServerManager(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue165);
            }
            ProxyConfigServerManager proxyConfigServerManager = (ProxyConfigServerManager) objRememberedValue165;
            Object objRememberedValue166 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue166 == companion10.getEmpty()) {
                objRememberedValue166 = new z01(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue166);
            }
            z01 z01Var = (z01) objRememberedValue166;
            State stateCollectAsState19 = SnapshotStateKt.collectAsState(z01Var.a(M(mutableState106).c()), SetsKt.emptySet(), (CoroutineContext) null, composerStartRestartGroup, 48, 2);
            Object objRememberedValue167 = composerStartRestartGroup.rememberedValue();
            z01 z01Var2 = z01Var;
            if (objRememberedValue167 == companion10.getEmpty()) {
                snapshotMutationPolicy2 = null;
                MutableState mutableStateMutableStateOf$default8 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default8);
                objRememberedValue167 = mutableStateMutableStateOf$default8;
            } else {
                snapshotMutationPolicy2 = null;
            }
            final MutableState mutableState108 = (MutableState) objRememberedValue167;
            Object objRememberedValue168 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue168 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default9 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default9);
                objRememberedValue168 = mutableStateMutableStateOf$default9;
            }
            final MutableState mutableState109 = (MutableState) objRememberedValue168;
            Object objRememberedValue169 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue169 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default10 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default10);
                objRememberedValue169 = mutableStateMutableStateOf$default10;
            }
            final MutableState mutableState110 = (MutableState) objRememberedValue169;
            Object objRememberedValue170 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue170 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default11 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default11);
                objRememberedValue170 = mutableStateMutableStateOf$default11;
            }
            final MutableState mutableState111 = (MutableState) objRememberedValue170;
            Object objRememberedValue171 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue171 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default12 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default12);
                objRememberedValue171 = mutableStateMutableStateOf$default12;
            }
            final MutableState mutableState112 = (MutableState) objRememberedValue171;
            Object objRememberedValue172 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue172 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default13 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default13);
                objRememberedValue172 = mutableStateMutableStateOf$default13;
            }
            final MutableState mutableState113 = (MutableState) objRememberedValue172;
            Object objRememberedValue173 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue173 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default14 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default14);
                objRememberedValue173 = mutableStateMutableStateOf$default14;
            }
            final MutableState mutableState114 = (MutableState) objRememberedValue173;
            Object objRememberedValue174 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue174 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default15 = SnapshotStateKt.mutableStateOf$default(CollectionsKt.emptyList(), snapshotMutationPolicy2, 2, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default15);
                objRememberedValue174 = mutableStateMutableStateOf$default15;
                i8 = 2;
            } else {
                i8 = 2;
            }
            final MutableState mutableState115 = (MutableState) objRememberedValue174;
            Object objRememberedValue175 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue175 == companion10.getEmpty()) {
                objRememberedValue175 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy2, snapshotMutationPolicy2, i8, snapshotMutationPolicy2);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue175);
            }
            final MutableState mutableState116 = (MutableState) objRememberedValue175;
            Object objRememberedValue176 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue176 == companion10.getEmpty()) {
                objRememberedValue176 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerStartRestartGroup);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue176);
            }
            CoroutineScope coroutineScope3 = (CoroutineScope) objRememberedValue176;
            Boolean bool = (Boolean) mutableState111.getValue();
            bool.getClass();
            ProxyConfig proxyConfigN = N(stateCollectAsState18);
            boolean zChangedInstance28 = composerStartRestartGroup.changedInstance(fs0Var) | composerStartRestartGroup.changedInstance(serverPreferences8);
            Object objRememberedValue177 = composerStartRestartGroup.rememberedValue();
            if (zChangedInstance28) {
                serverPreferences = serverPreferences8;
            } else {
                serverPreferences = serverPreferences8;
                if (objRememberedValue177 != companion10.getEmpty()) {
                    snapshotMutationPolicy3 = null;
                    j6Var = objRememberedValue177;
                }
                EffectsKt.LaunchedEffect(bool, proxyConfigN, (Function2) j6Var, composerStartRestartGroup, 0);
                objRememberedValue = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue == companion10.getEmpty()) {
                    i9 = 2;
                    objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, 2, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue);
                } else {
                    i9 = 2;
                }
                mutableState = (MutableState) objRememberedValue;
                objRememberedValue2 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue2 == companion10.getEmpty()) {
                    objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy3, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
                }
                mutableState2 = (MutableState) objRememberedValue2;
                objRememberedValue3 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue3 == companion10.getEmpty()) {
                    objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
                }
                mutableState3 = (MutableState) objRememberedValue3;
                objRememberedValue4 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue4 == companion10.getEmpty()) {
                    objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue4);
                }
                mutableState4 = (MutableState) objRememberedValue4;
                objRememberedValue5 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue5 == companion10.getEmpty()) {
                    objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue5);
                }
                mutableState5 = (MutableState) objRememberedValue5;
                objRememberedValue6 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue6 == companion10.getEmpty()) {
                    objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
                }
                mutableState6 = (MutableState) objRememberedValue6;
                objRememberedValue7 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue7 == companion10.getEmpty()) {
                    objRememberedValue7 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue7);
                }
                mutableState7 = (MutableState) objRememberedValue7;
                objRememberedValue8 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue8 == companion10.getEmpty()) {
                    objRememberedValue8 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
                }
                mutableState8 = (MutableState) objRememberedValue8;
                objRememberedValue9 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue9 == companion10.getEmpty()) {
                    objRememberedValue9 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
                }
                mutableState9 = (MutableState) objRememberedValue9;
                objRememberedValue10 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue10 == companion10.getEmpty()) {
                    objRememberedValue10 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue10);
                }
                mutableState10 = (MutableState) objRememberedValue10;
                objRememberedValue11 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue11 == companion10.getEmpty()) {
                    objRememberedValue11 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue11);
                }
                mutableState11 = (MutableState) objRememberedValue11;
                objRememberedValue12 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue12 == companion10.getEmpty()) {
                    objRememberedValue12 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue12);
                }
                mutableState12 = (MutableState) objRememberedValue12;
                objRememberedValue13 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue13 == companion10.getEmpty()) {
                    objRememberedValue13 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue13);
                }
                mutableState13 = (MutableState) objRememberedValue13;
                objRememberedValue14 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue14 == companion10.getEmpty()) {
                    objRememberedValue14 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue14);
                }
                mutableState14 = (MutableState) objRememberedValue14;
                objRememberedValue15 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue15 == companion10.getEmpty()) {
                    objRememberedValue15 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue15);
                }
                mutableState15 = (MutableState) objRememberedValue15;
                objRememberedValue16 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue16 == companion10.getEmpty()) {
                    objRememberedValue16 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue16);
                }
                mutableState16 = (MutableState) objRememberedValue16;
                objRememberedValue17 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue17 == companion10.getEmpty()) {
                    objRememberedValue17 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue17);
                }
                mutableState17 = (MutableState) objRememberedValue17;
                objRememberedValue18 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue18 == companion10.getEmpty()) {
                    objRememberedValue18 = new v52(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue18);
                }
                v52Var = (v52) objRememberedValue18;
                serverPreferences2 = serverPreferences;
                stateCollectAsState = SnapshotStateKt.collectAsState(v52Var.d, CollectionsKt.emptyList(), (CoroutineContext) null, composerStartRestartGroup, 48, 2);
                stateCollectAsState2 = SnapshotStateKt.collectAsState(v52Var.e, (Object) null, (CoroutineContext) null, composerStartRestartGroup, 48, 2);
                objRememberedValue19 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue19 == companion10.getEmpty()) {
                    objRememberedValue19 = new SubtitleFontUploadServerManager(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue19);
                }
                subtitleFontUploadServerManager = (SubtitleFontUploadServerManager) objRememberedValue19;
                objRememberedValue20 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue20 == companion10.getEmpty()) {
                    snapshotMutationPolicy4 = null;
                    MutableState mutableStateMutableStateOf$default16 = SnapshotStateKt.mutableStateOf$default((Object) null, (SnapshotMutationPolicy) null, 2, (Object) null);
                    composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default16);
                    obj = mutableStateMutableStateOf$default16;
                } else {
                    obj = objRememberedValue20;
                    snapshotMutationPolicy4 = null;
                }
                mutableState18 = (MutableState) obj;
                objRememberedValue21 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue21 == companion10.getEmpty()) {
                    MutableState mutableStateMutableStateOf$default17 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy4, 2, snapshotMutationPolicy4);
                    composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default17);
                    objRememberedValue21 = mutableStateMutableStateOf$default17;
                    i10 = 2;
                } else {
                    i10 = 2;
                }
                mutableState19 = (MutableState) objRememberedValue21;
                objRememberedValue22 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue22 == companion10.getEmpty()) {
                    objRememberedValue22 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy4, snapshotMutationPolicy4, i10, snapshotMutationPolicy4);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue22);
                }
                mutableState20 = (MutableState) objRememberedValue22;
                objRememberedValue23 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue23 == companion10.getEmpty()) {
                    objRememberedValue23 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy4, snapshotMutationPolicy4, i10, snapshotMutationPolicy4);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue23);
                }
                mutableState21 = (MutableState) objRememberedValue23;
                objRememberedValue24 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue24 == companion10.getEmpty()) {
                    objRememberedValue24 = new cc1(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue24);
                }
                cc1Var = (cc1) objRememberedValue24;
                stateCollectAsState3 = SnapshotStateKt.collectAsState(cc1Var.c, (fb1) cc1Var.b.getValue(), (CoroutineContext) null, composerStartRestartGroup, 0, 2);
                objRememberedValue25 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue25 == companion10.getEmpty()) {
                    objRememberedValue25 = new OnlineSubtitleConfigServerManager(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue25);
                }
                onlineSubtitleConfigServerManager = (OnlineSubtitleConfigServerManager) objRememberedValue25;
                objRememberedValue26 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue26 == companion10.getEmpty()) {
                    objRememberedValue26 = new oh2(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue26);
                }
                oh2Var = (oh2) objRememberedValue26;
                stateCollectAsState4 = SnapshotStateKt.collectAsState(oh2Var.c, (ug2) oh2Var.b.getValue(), (CoroutineContext) null, composerStartRestartGroup, 0, 2);
                objRememberedValue27 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue27 == companion10.getEmpty()) {
                    objRememberedValue27 = new nh2(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue27);
                }
                nh2Var = (nh2) objRememberedValue27;
                objRememberedValue28 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue28 == companion10.getEmpty()) {
                    objRememberedValue28 = new WebDavSyncConfigServerManager(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue28);
                }
                webDavSyncConfigServerManager = (WebDavSyncConfigServerManager) objRememberedValue28;
                objRememberedValue29 = composerStartRestartGroup.rememberedValue();
                if (objRememberedValue29 == companion10.getEmpty()) {
                    objRememberedValue29 = new vc2(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue29);
                }
                vc2 vc2Var3 = (vc2) objRememberedValue29;
                stateCollectAsState5 = SnapshotStateKt.collectAsState(vc2Var3.b, vc2Var3.a(), (CoroutineContext) null, composerStartRestartGroup, 0, 2);
                objRememberedValue30 = composerStartRestartGroup.rememberedValue();
                vc2Var = vc2Var3;
                if (objRememberedValue30 == companion10.getEmpty()) {
                    objRememberedValue30 = new sc2(context5);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue30);
                }
                sc2Var = (sc2) objRememberedValue30;
                bdVarB = id.b(composerStartRestartGroup);
                Boolean bool2 = (Boolean) state10.getValue();
                bool2.getClass();
                zChanged = composerStartRestartGroup.changed(bdVarB);
                objRememberedValue31 = composerStartRestartGroup.rememberedValue();
                if (zChanged || objRememberedValue31 == companion10.getEmpty()) {
                    objRememberedValue31 = new l6(bdVarB, state10, null, 1);
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue31);
                }
                EffectsKt.LaunchedEffect(bool2, (Function2) objRememberedValue31, composerStartRestartGroup, 0);
                Boolean bool3 = (Boolean) mutableState12.getValue();
                bool3.getClass();
                zChangedInstance = composerStartRestartGroup.changedInstance(subtitleFontUploadServerManager) | composerStartRestartGroup.changedInstance(v52Var);
                objRememberedValue32 = composerStartRestartGroup.rememberedValue();
                if (!zChangedInstance || objRememberedValue32 == companion10.getEmpty()) {
                    objRememberedValue32 = new n6(subtitleFontUploadServerManager, mutableState12, mutableState18, mutableState19, v52Var, mutableState21, mutableState11, null, 1);
                    v52Var2 = v52Var;
                    composerStartRestartGroup.updateRememberedValue(objRememberedValue32);
                } else {
                    v52Var2 = v52Var;
                }
                EffectsKt.LaunchedEffect(bool3, (Function2) objRememberedValue32, composerStartRestartGroup, 0);
                String strC3 = M(mutableState106).c();
                Boolean bool4 = (Boolean) state9.getValue();
                bool4.getClass();
                boolean zChanged12 = composerStartRestartGroup.changed(mutableState106) | composerStartRestartGroup.changedInstance(fs0Var) | composerStartRestartGroup.changedInstance(context5);
                i11 = i31 & 896;
                if (i11 == 256) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                z3 = zChanged12 | z2;
                objRememberedValue33 = composerStartRestartGroup.rememberedValue();
                if (!z3 || objRememberedValue33 == companion10.getEmpty()) {
                    composer3 = composerStartRestartGroup;
                    mutableState22 = mutableState106;
                    rq0 rq0Var = new rq0(fs0Var, context5, z, mutableState22, (MutableState) state9, (Continuation) null);
                    fs0Var2 = fs0Var;
                    context = context5;
                    z4 = z;
                    state = state9;
                    composer3.updateRememberedValue(rq0Var);
                    objRememberedValue33 = rq0Var;
                } else {
                    fs0Var2 = fs0Var;
                    mutableState22 = mutableState106;
                    composer3 = composerStartRestartGroup;
                    state = state9;
                    context = context5;
                    z4 = z;
                }
                EffectsKt.LaunchedEffect(strC3, bool4, (Function2) objRememberedValue33, composer3, 0);
                Boolean boolValueOf = Boolean.valueOf(z4);
                if (i11 == 256) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                zChangedInstance2 = z5 | composer3.changedInstance(fs0Var2);
                objRememberedValue34 = composer3.rememberedValue();
                if (zChangedInstance2 || objRememberedValue34 == companion10.getEmpty()) {
                    objRememberedValue34 = new pq0(z4, fs0Var2, null, 4);
                    composer3.updateRememberedValue(objRememberedValue34);
                }
                i12 = (i31 >> 6) & 14;
                EffectsKt.LaunchedEffect(boolValueOf, (Function2) objRememberedValue34, composer3, i12);
                to0 to0VarH = H(mutableState87);
                objRememberedValue35 = composer3.rememberedValue();
                if (objRememberedValue35 == companion10.getEmpty()) {
                    i13 = 0;
                    objRememberedValue35 = new mq0(mutableState87, null, 0);
                    composer3.updateRememberedValue(objRememberedValue35);
                } else {
                    i13 = 0;
                }
                EffectsKt.LaunchedEffect(to0VarH, (Function2) objRememberedValue35, composer3, i13);
                String str2 = (String) stateCollectAsState8.getValue();
                to0VarL = l(stateCollectAsState6);
                zChanged2 = composer3.changed(stateCollectAsState8) | composer3.changed(stateCollectAsState6);
                Object objRememberedValue178 = composer3.rememberedValue();
                if (!zChanged2 || objRememberedValue178 == companion10.getEmpty()) {
                    to0Var = to0VarL;
                    mutableState23 = mutableState87;
                    state2 = stateCollectAsState8;
                    j6Var2 = new j6((Object) state2, mutableState23, stateCollectAsState6, (Continuation) null, 3);
                    composer3.updateRememberedValue(j6Var2);
                } else {
                    j6Var2 = objRememberedValue178;
                    to0Var = to0VarL;
                    mutableState23 = mutableState87;
                    state2 = stateCollectAsState8;
                }
                EffectsKt.LaunchedEffect(str2, to0Var, (Function2) j6Var2, composer3, 0);
                Boolean boolValueOf2 = Boolean.valueOf(z4);
                to0 to0VarH2 = H(mutableState23);
                String str3 = (String) state2.getValue();
                Boolean boolValueOf3 = Boolean.valueOf(D(stateCollectAsState12));
                Boolean bool5 = (Boolean) mutableState107.getValue();
                bool5.getClass();
                Object[] objArr4 = {boolValueOf2, to0VarH2, str3, boolValueOf3, bool5};
                if (i11 == 256) {
                    z6 = true;
                } else {
                    z6 = false;
                }
                zChanged3 = z6 | composer3.changed(mutableState107) | composer3.changed(state2) | composer3.changed(stateCollectAsState12);
                Object objRememberedValue179 = composer3.rememberedValue();
                if (!zChanged3 || objRememberedValue179 == companion10.getEmpty()) {
                    State state12 = state2;
                    nq0Var = new nq0(z4, mutableState23, mutableState107, state12, stateCollectAsState12, state8, mutableState91, null);
                    mutableState24 = mutableState107;
                    state2 = state12;
                    composer3.updateRememberedValue(nq0Var);
                } else {
                    nq0Var = objRememberedValue179;
                    mutableState24 = mutableState107;
                }
                EffectsKt.LaunchedEffect(objArr4, (Function2) nq0Var, composer3, 0);
                Object[] objArr5 = {Boolean.valueOf(z), H(mutableState23), Boolean.valueOf(D(stateCollectAsState12)), E(stateCollectAsState13), (String) state2.getValue()};
                if (i11 == 256) {
                    z7 = true;
                } else {
                    z7 = false;
                }
                zChanged4 = z7 | composer3.changed(stateCollectAsState12) | composer3.changed(state2) | composer3.changed(stateCollectAsState13) | composer3.changed(mutableState24);
                Object objRememberedValue180 = composer3.rememberedValue();
                if (!zChanged4 || objRememberedValue180 == companion10.getEmpty()) {
                    State state13 = state2;
                    oq0Var = new oq0(z, mutableState23, stateCollectAsState12, state13, stateCollectAsState13, mutableState24, state8, mutableState91, mutableState90, null);
                    z8 = z;
                    state3 = state13;
                    composer3.updateRememberedValue(oq0Var);
                } else {
                    oq0Var = objRememberedValue180;
                    state3 = state2;
                    z8 = z;
                }
                EffectsKt.LaunchedEffect(objArr5, (Function2) oq0Var, composer3, 0);
                Boolean boolValueOf4 = Boolean.valueOf(z8);
                if (i11 == 256) {
                    z9 = true;
                } else {
                    z9 = false;
                }
                objRememberedValue36 = composer3.rememberedValue();
                if (!z9 || objRememberedValue36 == companion10.getEmpty()) {
                    mutableState25 = mutableState94;
                    continuation = null;
                    objRememberedValue36 = new pq0(z8, mutableState25, continuation, 0);
                    composer3.updateRememberedValue(objRememberedValue36);
                } else {
                    mutableState25 = mutableState94;
                    continuation = null;
                }
                EffectsKt.LaunchedEffect(boolValueOf4, (Function2) objRememberedValue36, composer3, i12);
                strC = M(mutableState22).c();
                if (strC != null) {
                    serverById = serverPreferences2.getServerById(strC);
                } else {
                    serverById = continuation;
                }
                Boolean bool6 = (Boolean) mutableState110.getValue();
                bool6.getClass();
                ProxyConfig proxyConfigN2 = N(stateCollectAsState18);
                zChangedInstance3 = composer3.changedInstance(serverById) | composer3.changedInstance(fs0Var2);
                objRememberedValue37 = composer3.rememberedValue();
                if (!zChangedInstance3 || objRememberedValue37 == companion10.getEmpty()) {
                    serverPreferences3 = serverPreferences2;
                    fs0 fs0Var4 = fs0Var2;
                    j6 j6Var3 = new j6(serverById, fs0Var4, mutableState110, (Continuation) null, 4);
                    fs0Var3 = fs0Var4;
                    composer3.updateRememberedValue(j6Var3);
                    objRememberedValue37 = j6Var3;
                } else {
                    fs0Var3 = fs0Var2;
                    serverPreferences3 = serverPreferences2;
                }
                Composer composer15 = composer3;
                EffectsKt.LaunchedEffect(bool6, serverById, proxyConfigN2, (Function2) objRememberedValue37, composer15, 0);
                composer2 = composer15;
                Unit unit = Unit.INSTANCE;
                zChangedInstance4 = composer2.changedInstance(fs0Var3);
                objRememberedValue38 = composer2.rememberedValue();
                if (zChangedInstance4 || objRememberedValue38 == companion10.getEmpty()) {
                    objRememberedValue38 = new cp0(fs0Var3, 0);
                    composer2.updateRememberedValue(objRememberedValue38);
                }
                EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue38, composer2, 6);
                if (serverById != 0) {
                    listCreateListBuilder = CollectionsKt.createListBuilder();
                    String displayAddress = serverById.getDisplayAddress();
                    if (M(mutableState22).e == null) {
                        z46 = true;
                    } else {
                        z46 = false;
                    }
                    listCreateListBuilder.add(new vo0(null, "主线路", displayAddress, z46, true, null, null, null, (ServerPingState) ((Map) stateCollectAsState17.getValue()).get(null), null, 1504));
                    for (it = serverById.getBackupRoutesSafe().iterator(); it.hasNext(); it = it) {
                        BackupRouteConfig backupRouteConfig = (BackupRouteConfig) it.next();
                        String id = backupRouteConfig.getId();
                        alias = backupRouteConfig.getAlias();
                        if (StringsKt.isBlank(alias)) {
                            alias = "未命名线路";
                        }
                        listCreateListBuilder.add(new vo0(id, alias, backupRouteConfig.getDisplayAddress(), Intrinsics.areEqual(M(mutableState22).e, backupRouteConfig.getId()), false, null, null, null, (ServerPingState) ((Map) stateCollectAsState17.getValue()).get(backupRouteConfig.getId()), null, 1504));
                    }
                    listEmptyList = CollectionsKt.build(listCreateListBuilder);
                } else {
                    listEmptyList = null;
                }
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                list = listEmptyList;
                listA = r02.a(serverPreferences3.getAllServers(), M(mutableState22).c(), ((Boolean) mutableState95.getValue()).booleanValue(), false, G(stateCollectAsState16), "查看、添加或编辑服务器");
                if (z8 || ((Boolean) bdVarB.e.getValue()).booleanValue() || bdVarB.e() || ((String) bdVarB.g.getValue()) != null) {
                    z10 = false;
                } else {
                    z10 = true;
                }
                objRememberedValue39 = composer2.rememberedValue();
                companion = Composer.Companion;
                if (objRememberedValue39 == companion.getEmpty()) {
                    mutableState26 = mutableState111;
                    objRememberedValue39 = new Function0() { // from class: dp0
                        public final Object invoke() {
                            MutableState mutableState117;
                            Number number;
                            MutableState mutableState118 = mutableState23;
                            to0 to0Var2 = (to0) mutableState118.getValue();
                            MutableState mutableState119 = mutableState111;
                            if (((Boolean) mutableState119.getValue()).booleanValue()) {
                                mutableState119.setValue(Boolean.FALSE);
                            } else {
                                MutableState mutableState120 = mutableState12;
                                boolean zBooleanValue2 = ((Boolean) mutableState120.getValue()).booleanValue();
                                MutableState mutableState121 = mutableState11;
                                if (zBooleanValue2) {
                                    mutableState120.setValue(Boolean.FALSE);
                                    Unit unit2 = Unit.INSTANCE;
                                    hr0.q(mutableState121, true);
                                } else {
                                    MutableState mutableState122 = mutableState20;
                                    if (((String) mutableState122.getValue()) != null) {
                                        mutableState122.setValue((Object) null);
                                    } else {
                                        MutableState mutableState123 = mutableState21;
                                        if (((String) mutableState123.getValue()) != null) {
                                            mutableState123.setValue((Object) null);
                                        } else {
                                            MutableState mutableState124 = mutableState3;
                                            boolean zBooleanValue3 = ((Boolean) mutableState124.getValue()).booleanValue();
                                            MutableState mutableState125 = mutableState;
                                            if (zBooleanValue3) {
                                                mutableState124.setValue(Boolean.FALSE);
                                                Unit unit3 = Unit.INSTANCE;
                                                hr0.n(mutableState125, true);
                                            } else {
                                                MutableState mutableState126 = mutableState4;
                                                if (((Boolean) mutableState126.getValue()).booleanValue()) {
                                                    mutableState126.setValue(Boolean.FALSE);
                                                    Unit unit4 = Unit.INSTANCE;
                                                    hr0.n(mutableState125, true);
                                                } else {
                                                    MutableState mutableState127 = mutableState5;
                                                    if (((Boolean) mutableState127.getValue()).booleanValue()) {
                                                        mutableState127.setValue(Boolean.FALSE);
                                                        Unit unit5 = Unit.INSTANCE;
                                                        hr0.n(mutableState125, true);
                                                    } else {
                                                        MutableState mutableState128 = mutableState6;
                                                        if (((Boolean) mutableState128.getValue()).booleanValue()) {
                                                            mutableState128.setValue(Boolean.FALSE);
                                                            Unit unit6 = Unit.INSTANCE;
                                                            hr0.n(mutableState125, true);
                                                        } else {
                                                            MutableState mutableState129 = mutableState7;
                                                            if (((Boolean) mutableState129.getValue()).booleanValue()) {
                                                                mutableState129.setValue(Boolean.FALSE);
                                                                Unit unit7 = Unit.INSTANCE;
                                                                hr0.n(mutableState125, true);
                                                            } else {
                                                                MutableState mutableState130 = mutableState8;
                                                                if (((Boolean) mutableState130.getValue()).booleanValue()) {
                                                                    mutableState130.setValue(Boolean.FALSE);
                                                                    Unit unit8 = Unit.INSTANCE;
                                                                    hr0.n(mutableState125, true);
                                                                } else {
                                                                    MutableState mutableState131 = mutableState9;
                                                                    if (((Boolean) mutableState131.getValue()).booleanValue()) {
                                                                        mutableState131.setValue(Boolean.FALSE);
                                                                        Unit unit9 = Unit.INSTANCE;
                                                                        hr0.n(mutableState125, true);
                                                                    } else {
                                                                        MutableState mutableState132 = mutableState10;
                                                                        if (((Boolean) mutableState132.getValue()).booleanValue()) {
                                                                            mutableState132.setValue(Boolean.FALSE);
                                                                            Unit unit10 = Unit.INSTANCE;
                                                                            hr0.n(mutableState125, true);
                                                                        } else if (((Boolean) mutableState121.getValue()).booleanValue()) {
                                                                            mutableState121.setValue(Boolean.FALSE);
                                                                            Unit unit11 = Unit.INSTANCE;
                                                                            hr0.n(mutableState125, true);
                                                                        } else {
                                                                            MutableState mutableState133 = mutableState13;
                                                                            if (((Boolean) mutableState133.getValue()).booleanValue()) {
                                                                                mutableState133.setValue(Boolean.FALSE);
                                                                                Unit unit12 = Unit.INSTANCE;
                                                                                hr0.n(mutableState125, true);
                                                                            } else {
                                                                                MutableState mutableState134 = mutableState14;
                                                                                if (((Boolean) mutableState134.getValue()).booleanValue()) {
                                                                                    mutableState134.setValue(Boolean.FALSE);
                                                                                    Unit unit13 = Unit.INSTANCE;
                                                                                    hr0.n(mutableState125, true);
                                                                                } else {
                                                                                    MutableState mutableState135 = mutableState15;
                                                                                    if (((Boolean) mutableState135.getValue()).booleanValue()) {
                                                                                        mutableState135.setValue(Boolean.FALSE);
                                                                                        Unit unit14 = Unit.INSTANCE;
                                                                                        hr0.n(mutableState125, true);
                                                                                    } else {
                                                                                        MutableState mutableState136 = mutableState17;
                                                                                        if (((Boolean) mutableState136.getValue()).booleanValue()) {
                                                                                            mutableState136.setValue(Boolean.FALSE);
                                                                                            Unit unit15 = Unit.INSTANCE;
                                                                                            hr0.n(mutableState125, true);
                                                                                        } else if (((Boolean) mutableState125.getValue()).booleanValue()) {
                                                                                            mutableState125.setValue(Boolean.FALSE);
                                                                                            mutableState2.setValue((Object) null);
                                                                                        } else if (!((Boolean) mutableState16.getValue()).booleanValue()) {
                                                                                            MutableState mutableState137 = mutableState110;
                                                                                            if (((Boolean) mutableState137.getValue()).booleanValue()) {
                                                                                                mutableState137.setValue(Boolean.FALSE);
                                                                                            } else {
                                                                                                MutableState mutableState138 = mutableState108;
                                                                                                if (((Boolean) mutableState138.getValue()).booleanValue()) {
                                                                                                    mutableState138.setValue(Boolean.FALSE);
                                                                                                    hr0.k(mutableState109, false);
                                                                                                } else {
                                                                                                    MutableState mutableState139 = mutableState112;
                                                                                                    if (((Boolean) mutableState139.getValue()).booleanValue()) {
                                                                                                        mutableState139.setValue(Boolean.FALSE);
                                                                                                    } else {
                                                                                                        MutableState mutableState140 = mutableState114;
                                                                                                        boolean zBooleanValue4 = ((Boolean) mutableState140.getValue()).booleanValue();
                                                                                                        MutableState mutableState141 = mutableState115;
                                                                                                        if (zBooleanValue4) {
                                                                                                            hr0.h(mutableState116, mutableState140, mutableState141);
                                                                                                        } else {
                                                                                                            MutableState mutableState142 = mutableState113;
                                                                                                            if (((Boolean) mutableState142.getValue()).booleanValue()) {
                                                                                                                mutableState142.setValue(Boolean.FALSE);
                                                                                                                mutableState141.setValue(CollectionsKt.emptyList());
                                                                                                            } else {
                                                                                                                MutableState mutableState143 = mutableState89;
                                                                                                                if (((Boolean) mutableState143.getValue()).booleanValue()) {
                                                                                                                    mutableState143.setValue(Boolean.FALSE);
                                                                                                                } else {
                                                                                                                    to0 to0Var3 = to0.a;
                                                                                                                    MutableState mutableState144 = state8;
                                                                                                                    if (to0Var2 != to0Var3) {
                                                                                                                        mutableState118.setValue(to0Var3);
                                                                                                                        hr0.p(mutableState144, true);
                                                                                                                        MutableState mutableState145 = mutableState91;
                                                                                                                        zr.A((Number) mutableState145.getValue(), 1, mutableState145);
                                                                                                                        mutableState143.setValue(Boolean.FALSE);
                                                                                                                    } else if (!((Boolean) mutableState104.getValue()).booleanValue() || ((Boolean) mutableState25.getValue()).booleanValue()) {
                                                                                                                        mutableState143.setValue(Boolean.TRUE);
                                                                                                                    } else {
                                                                                                                        if (((Boolean) mutableState144.getValue()).booleanValue()) {
                                                                                                                            mutableState117 = mutableState90;
                                                                                                                            number = (Number) mutableState117.getValue();
                                                                                                                        } else {
                                                                                                                            mutableState117 = mutableState93;
                                                                                                                            number = (Number) mutableState117.getValue();
                                                                                                                        }
                                                                                                                        zr.A(number, 1, mutableState117);
                                                                                                                        mutableState143.setValue(Boolean.FALSE);
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    mutableState27 = mutableState12;
                    composer2.updateRememberedValue(objRememberedValue39);
                } else {
                    mutableState26 = mutableState111;
                    mutableState27 = mutableState12;
                }
                bdVar = bdVarB;
                mutableState28 = mutableState26;
                BackHandlerKt.BackHandler(z10, (Function0) objRememberedValue39, composer2, 48, 0);
                Boolean boolValueOf5 = Boolean.valueOf(z8);
                if (i11 == 256) {
                    z11 = true;
                } else {
                    z11 = false;
                }
                zChangedInstance5 = z11 | composer2.changedInstance(fs0Var3);
                objRememberedValue40 = composer2.rememberedValue();
                if (zChangedInstance5 || objRememberedValue40 == companion.getEmpty()) {
                    final int i32 = 0;
                    objRememberedValue40 = new Function1() { // from class: yp0
                        public final Object invoke(Object obj6) {
                            int i33 = i32;
                            Object obj7 = fs0Var3;
                            boolean z48 = z8;
                            switch (i33) {
                                case 0:
                                    ((DisposableEffectScope) obj6).getClass();
                                    return new uq0(z48, (fs0) obj7);
                                default:
                                    return SliderKt.b(z48, (SliderState) obj7, (SemanticsPropertyReceiver) obj6);
                            }
                        }
                    };
                    composer2.updateRememberedValue(objRememberedValue40);
                }
                EffectsKt.DisposableEffect(boolValueOf5, (Function1) objRememberedValue40, composer2, i12);
                modifier = Modifier.Companion;
                state4 = state10;
                Modifier modifierFillMaxSize$default = SizeKt.fillMaxSize$default(modifier, 0.0f, 1, (Object) null);
                if (i11 == 256) {
                    z12 = true;
                } else {
                    z12 = false;
                }
                objRememberedValue41 = composer2.rememberedValue();
                if (z12 || objRememberedValue41 == companion.getEmpty()) {
                    objRememberedValue41 = new aq0(z8, 0);
                    composer2.updateRememberedValue(objRememberedValue41);
                }
                Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierFillMaxSize$default, (Function1) objRememberedValue41);
                if (i11 == 256) {
                    z13 = true;
                } else {
                    z13 = false;
                }
                zChanged5 = z13 | composer2.changed(stateCollectAsState12) | composer2.changedInstance(fs0Var3);
                objRememberedValue42 = composer2.rememberedValue();
                if (zChanged5 || objRememberedValue42 == companion.getEmpty()) {
                    objRememberedValue42 = new qq0(z8, fs0Var3, mutableState23, stateCollectAsState12);
                    composer2.updateRememberedValue(objRememberedValue42);
                }
                Modifier modifierOnPreviewKeyEvent = KeyInputModifierKt.onPreviewKeyEvent(modifierGraphicsLayer, (Function1) objRememberedValue42);
                companion2 = Alignment.Companion;
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(companion2.getTopStart(), false);
                int iB = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
                CompositionLocalMap currentCompositionLocalMap = composer2.getCurrentCompositionLocalMap();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierOnPreviewKeyEvent);
                companion3 = ComposeUiNode.Companion;
                constructor = companion3.getConstructor();
                if (!pd.n(composer2.getApplier())) {
                    ComposablesKt.invalidApplier();
                }
                composer2.startReusableNode();
                if (composer2.getInserting()) {
                    composer2.createNode(constructor);
                } else {
                    composer2.useNode();
                }
                Composer composer16 = Updater.constructor-impl(composer2);
                zr.z(companion3, composer16, measurePolicyMaybeCachedBoxMeasurePolicy, composer16, currentCompositionLocalMap);
                Updater.set-impl(composer16, modifierMaterializeModifier, pd.h(companion3, composer16, Integer.valueOf(iB), composer16));
                boxScopeInstance = BoxScopeInstance.INSTANCE;
                Modifier modifierFocusGroup = FocusableKt.focusGroup(FocusRequesterModifierKt.focusRequester(SizeKt.fillMaxSize$default(modifier, 0.0f, 1, (Object) null), focusRequester));
                arrangement = Arrangement.INSTANCE;
                MeasurePolicy measurePolicyColumnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement.getTop(), companion2.getStart(), composer2, 0);
                int iB2 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
                CompositionLocalMap currentCompositionLocalMap2 = composer2.getCurrentCompositionLocalMap();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer2, modifierFocusGroup);
                constructor2 = companion3.getConstructor();
                if (!pd.n(composer2.getApplier())) {
                    ComposablesKt.invalidApplier();
                }
                composer2.startReusableNode();
                if (composer2.getInserting()) {
                    composer2.createNode(constructor2);
                } else {
                    composer2.useNode();
                }
                Composer composer17 = Updater.constructor-impl(composer2);
                zr.z(companion3, composer17, measurePolicyColumnMeasurePolicy, composer17, currentCompositionLocalMap2);
                Updater.set-impl(composer17, modifierMaterializeModifier2, pd.h(companion3, composer17, Integer.valueOf(iB2), composer17));
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                iOrdinal = H(mutableState23).ordinal();
                if (iOrdinal != 0) {
                    if (iOrdinal != 1) {
                        serverPreferences7 = serverPreferences3;
                        mutableState86 = mutableState23;
                        bdVar = bdVar;
                        state4 = state4;
                        i14 = i29;
                        composer2.startReplaceGroup(-1687220673);
                        boolean zBooleanValue2 = ((Boolean) mutableState99.getValue()).booleanValue();
                        boolean zBooleanValue3 = ((Boolean) mutableState100.getValue()).booleanValue();
                        String strL = L(mutableState88);
                        zChanged11 = composer2.changed(mutableState88);
                        objRememberedValue130 = composer2.rememberedValue();
                        if (zChanged11 || objRememberedValue130 == companion.getEmpty()) {
                            objRememberedValue130 = new z70(24, mutableState88);
                            composer2.updateRememberedValue(objRememberedValue130);
                        }
                        Function1 function14 = (Function1) objRememberedValue130;
                        objRememberedValue131 = composer2.rememberedValue();
                        if (objRememberedValue131 == companion.getEmpty()) {
                            objRememberedValue131 = new z70(25, r79);
                            composer2.updateRememberedValue(objRememberedValue131);
                        }
                        Function1 function15 = (Function1) objRememberedValue131;
                        boolean zChangedInstance29 = composer2.changedInstance(fs0Var3);
                        if ((i14 & 112) == 32) {
                            z39 = true;
                        } else {
                            z39 = false;
                        }
                        z40 = zChangedInstance29 | z39;
                        objRememberedValue132 = composer2.rememberedValue();
                        if (z40 || objRememberedValue132 == companion.getEmpty()) {
                            final int i33 = 1;
                            objRememberedValue132 = new Function1() { // from class: cq0
                                public final Object invoke(Object obj6) {
                                    int i34 = i33;
                                    MutableState mutableState117 = mutableState86;
                                    Function1 function16 = function2;
                                    fs0 fs0Var5 = fs0Var3;
                                    String str4 = (String) obj6;
                                    switch (i34) {
                                        case 0:
                                            str4.getClass();
                                            Objects.toString((to0) mutableState117.getValue());
                                            fs0Var5.R((to0) mutableState117.getValue());
                                            fs0.Q(fs0Var5, str4, "我的媒体", uo0.b, 12);
                                            function16.invoke(str4);
                                            break;
                                        case 1:
                                            str4.getClass();
                                            Objects.toString((to0) mutableState117.getValue());
                                            fs0Var5.R((to0) mutableState117.getValue());
                                            fs0.Q(fs0Var5, str4, null, null, 30);
                                            function16.invoke(str4);
                                            break;
                                        default:
                                            str4.getClass();
                                            Objects.toString((to0) mutableState117.getValue());
                                            fs0Var5.R((to0) mutableState117.getValue());
                                            fs0.Q(fs0Var5, str4, null, null, 30);
                                            function16.invoke(str4);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue132);
                        }
                        zk0.b(z, zBooleanValue2, zBooleanValue3, strL, function14, function15, function3, (Function1) objRememberedValue132, null, composer2, i12 | ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE | ((i14 << 12) & 3670016));
                        composer2 = composer2;
                        composer2.endReplaceGroup();
                        Unit unit2 = Unit.INSTANCE;
                    } else {
                        if (iOrdinal == 2) {
                            composer2.startReplaceGroup(360892379);
                            composer2.endReplaceGroup();
                            dv.f();
                            return;
                        }
                        composer2.startReplaceGroup(-1686184064);
                        if (zBooleanValue) {
                            composer2.startReplaceGroup(-1686123273);
                            objRememberedValue134 = composer2.rememberedValue();
                            if (objRememberedValue134 == companion.getEmpty()) {
                                objRememberedValue134 = new hp0(11);
                                composer2.updateRememberedValue(objRememberedValue134);
                            }
                            Function0 function16 = (Function0) objRememberedValue134;
                            boolean zChangedInstance30 = composer2.changedInstance(serverPreferences3) | composer2.changedInstance(fs0Var3);
                            i14 = i29;
                            if ((i14 & 7168) == 2048) {
                                z43 = true;
                            } else {
                                z43 = false;
                            }
                            boolean z48 = zChangedInstance30 | z43;
                            if ((i14 & 112) == 32) {
                                z44 = true;
                            } else {
                                z44 = false;
                            }
                            z45 = z48 | z44;
                            objRememberedValue135 = composer2.rememberedValue();
                            if (!z45 || objRememberedValue135 == companion.getEmpty()) {
                                ServerPreferences serverPreferences9 = serverPreferences3;
                                fd fdVar = new fd(4, serverPreferences9, fs0Var3, function4, function2);
                                serverPreferences7 = serverPreferences9;
                                composer2.updateRememberedValue(fdVar);
                                objRememberedValue135 = fdVar;
                            } else {
                                serverPreferences7 = serverPreferences3;
                            }
                            mutableState86 = mutableState23;
                            z8.a(function16, (Function2) objRememberedValue135, ((Boolean) mutableState102.getValue()).booleanValue(), z, false, PaddingKt.padding-qDBjuR0$default(modifier, 0.0f, Dp.constructor-impl(64.0f), 0.0f, 0.0f, 13, (Object) null), null, composer2, ((i31 << 3) & 7168) | 221190);
                            composer2 = composer2;
                            composer2.endReplaceGroup();
                        } else {
                            serverPreferences7 = serverPreferences3;
                            mutableState86 = mutableState23;
                            bdVar = bdVar;
                            state4 = state4;
                            i14 = i29;
                            composer2.startReplaceGroup(-1684589238);
                            boolean zChangedInstance31 = composer2.changedInstance(fs0Var3);
                            if ((i14 & 112) == 32) {
                                z41 = true;
                            } else {
                                z41 = false;
                            }
                            z42 = zChangedInstance31 | z41;
                            objRememberedValue133 = composer2.rememberedValue();
                            if (z42 || objRememberedValue133 == companion.getEmpty()) {
                                final int i34 = 2;
                                objRememberedValue133 = new Function1() { // from class: cq0
                                    public final Object invoke(Object obj6) {
                                        int i35 = i34;
                                        MutableState mutableState117 = mutableState86;
                                        Function1 function17 = function2;
                                        fs0 fs0Var5 = fs0Var3;
                                        String str4 = (String) obj6;
                                        switch (i35) {
                                            case 0:
                                                str4.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, str4, "我的媒体", uo0.b, 12);
                                                function17.invoke(str4);
                                                break;
                                            case 1:
                                                str4.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, str4, null, null, 30);
                                                function17.invoke(str4);
                                                break;
                                            default:
                                                str4.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, str4, null, null, 30);
                                                function17.invoke(str4);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composer2.updateRememberedValue(objRememberedValue133);
                            }
                            lx1.b(z, (Function1) objRememberedValue133, null, composer2, i12);
                            composer2.endReplaceGroup();
                        }
                        composer2.endReplaceGroup();
                        Unit unit3 = Unit.INSTANCE;
                    }
                    mutableState31 = mutableState86;
                    serverPreferences4 = serverPreferences7;
                    boxScopeInstance = boxScopeInstance;
                    state8 = r79;
                    bdVar2 = bdVar;
                    state5 = stateCollectAsState15;
                    z01Var2 = z01Var2;
                    z22 = false;
                    str = null;
                } else {
                    serverPreferences4 = serverPreferences3;
                    mutableState29 = mutableState23;
                    state4 = state4;
                    state5 = stateCollectAsState15;
                    i14 = i29;
                    composer2.startReplaceGroup(-1697239098);
                    nr0Var = (nr0) stateCollectAsState7.getValue();
                    if (nr0Var instanceof lr0) {
                        composer2.startReplaceGroup(-1697475876);
                        Modifier modifierFillMaxSize$default2 = SizeKt.fillMaxSize$default(modifier, 0.0f, 1, (Object) null);
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(companion2.getCenter(), false);
                        int iB3 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
                        CompositionLocalMap currentCompositionLocalMap3 = composer2.getCurrentCompositionLocalMap();
                        Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxSize$default2);
                        constructor5 = companion3.getConstructor();
                        if (!pd.n(composer2.getApplier())) {
                            ComposablesKt.invalidApplier();
                        }
                        composer2.startReusableNode();
                        if (composer2.getInserting()) {
                            composer2.createNode(constructor5);
                        } else {
                            composer2.useNode();
                        }
                        Composer composer18 = Updater.constructor-impl(composer2);
                        zr.z(companion3, composer18, measurePolicyMaybeCachedBoxMeasurePolicy2, composer18, currentCompositionLocalMap3);
                        Updater.set-impl(composer18, modifierMaterializeModifier3, pd.h(companion3, composer18, Integer.valueOf(iB3), composer18));
                        ProgressIndicatorKt.CircularProgressIndicator-4lLiAd8((Modifier) null, 0L, 0.0f, 0L, 0, 0.0f, composer2, 0, 63);
                        composer2 = composer2;
                        composer2.endNode();
                        composer2.endReplaceGroup();
                        bdVar2 = bdVar;
                        state8 = r79;
                        mutableState31 = mutableState29;
                        z22 = false;
                        str = null;
                    } else {
                        bdVar2 = bdVar;
                        if (nr0Var instanceof kr0) {
                            composer2.startReplaceGroup(-1697072938);
                            objRememberedValue56 = composer2.rememberedValue();
                            if (objRememberedValue56 == companion.getEmpty()) {
                                obj4 = null;
                                objRememberedValue56 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                                composer2.updateRememberedValue(objRememberedValue56);
                            } else {
                                obj4 = null;
                            }
                            mutableState32 = (MutableState) objRememberedValue56;
                            Modifier modifierFillMaxSize$default3 = SizeKt.fillMaxSize$default(modifier, 0.0f, 1, obj4);
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(companion2.getCenter(), false);
                            int iB4 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
                            CompositionLocalMap currentCompositionLocalMap4 = composer2.getCurrentCompositionLocalMap();
                            Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxSize$default3);
                            constructor3 = companion3.getConstructor();
                            if (!pd.n(composer2.getApplier())) {
                                ComposablesKt.invalidApplier();
                            }
                            composer2.startReusableNode();
                            if (composer2.getInserting()) {
                                composer2.createNode(constructor3);
                            } else {
                                composer2.useNode();
                            }
                            Composer composer19 = Updater.constructor-impl(composer2);
                            zr.z(companion3, composer19, measurePolicyMaybeCachedBoxMeasurePolicy3, composer19, currentCompositionLocalMap4);
                            Updater.set-impl(composer19, modifierMaterializeModifier4, pd.h(companion3, composer19, Integer.valueOf(iB4), composer19));
                            MeasurePolicy measurePolicyP = zr.p(16.0f, arrangement, companion2.getCenterHorizontally(), composer2, 54);
                            int iB5 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
                            CompositionLocalMap currentCompositionLocalMap5 = composer2.getCurrentCompositionLocalMap();
                            Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composer2, modifier);
                            constructor4 = companion3.getConstructor();
                            if (!pd.n(composer2.getApplier())) {
                                ComposablesKt.invalidApplier();
                            }
                            composer2.startReusableNode();
                            if (composer2.getInserting()) {
                                composer2.createNode(constructor4);
                            } else {
                                composer2.useNode();
                            }
                            Composer composer20 = Updater.constructor-impl(composer2);
                            zr.z(companion3, composer20, measurePolicyP, composer20, currentCompositionLocalMap5);
                            Updater.set-impl(composer20, modifierMaterializeModifier5, pd.h(companion3, composer20, Integer.valueOf(iB5), composer20));
                            String strA = ((kr0) nr0Var).a();
                            materialTheme = MaterialTheme.INSTANCE;
                            i22 = MaterialTheme.$stable;
                            str = null;
                            TextKt.Text-Nvy7gAk(strA, (Modifier) null, materialTheme.getColorScheme(composer2, i22).getError-0d7_KjU(), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer2, i22).getBodyLarge(), composer2, 0, 0, 131066);
                            zChangedInstance8 = composer2.changedInstance(fs0Var3);
                            objRememberedValue57 = composer2.rememberedValue();
                            if (zChangedInstance8 || objRememberedValue57 == companion.getEmpty()) {
                                final int i35 = 0;
                                objRememberedValue57 = new Function0() { // from class: bq0
                                    public final Object invoke() throws NoSuchAlgorithmException {
                                        int i36 = i35;
                                        fs0 fs0Var5 = fs0Var3;
                                        switch (i36) {
                                            case 0:
                                                fs0Var5.G(false, false);
                                                break;
                                            case 1:
                                                fs0Var5.k.setValue(Boolean.FALSE);
                                                break;
                                            default:
                                                fs0Var5.G(false, false);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composer2.updateRememberedValue(objRememberedValue57);
                            }
                            Function0 function17 = (Function0) objRememberedValue57;
                            objRememberedValue58 = composer2.rememberedValue();
                            if (objRememberedValue58 == companion.getEmpty()) {
                                objRememberedValue58 = new z70(22, mutableState32);
                                composer2.updateRememberedValue(objRememberedValue58);
                            }
                            Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifier, (Function1) objRememberedValue58);
                            if (y(mutableState32)) {
                                composer2.startReplaceGroup(492049091);
                                modifier2 = BorderKt.border-xT4_qwU(modifier, Dp.constructor-impl(3.0f), Color.Companion.getWhite-0d7_KjU(), materialTheme.getShapes(composer2, i22).getMedium());
                                composer2.endReplaceGroup();
                            } else {
                                composer2.startReplaceGroup(492432096);
                                composer2.endReplaceGroup();
                                modifier2 = modifier;
                            }
                            ButtonKt.Button(function17, modifierOnFocusChanged.then(modifier2), false, (Shape) null, (ButtonColors) null, (ButtonElevation) null, (BorderStroke) null, (PaddingValues) null, (MutableInteractionSource) null, st.k(), composer2, 805306368, 508);
                            composer2.endNode();
                            composer2.endNode();
                            composer2.endReplaceGroup();
                            composer2 = composer2;
                            mutableState31 = mutableState29;
                            z22 = false;
                        } else {
                            str = null;
                            if (nr0Var instanceof mr0) {
                                composer2.startReplaceGroup(360892348);
                                composer2.endReplaceGroup();
                                dv.f();
                                return;
                            }
                            composer2.startReplaceGroup(-1694909231);
                            Boolean boolValueOf6 = Boolean.valueOf(z);
                            to0 to0VarH3 = H(mutableState29);
                            mr0Var = (mr0) nr0Var;
                            list2 = mr0Var.c;
                            list3 = mr0Var.a;
                            Object[] objArr6 = {boolValueOf6, to0VarH3, list3, (ItemCounts) state5.getValue()};
                            if (i11 == 256) {
                                z14 = true;
                            } else {
                                z14 = false;
                            }
                            zChanged6 = z14 | composer2.changed(nr0Var) | composer2.changed(state5) | composer2.changedInstance(fs0Var3);
                            objRememberedValue43 = composer2.rememberedValue();
                            if (!zChanged6 || objRememberedValue43 == companion.getEmpty()) {
                                mr0Var2 = mr0Var;
                                rq0 rq0Var2 = new rq0(z, mr0Var2, fs0Var3, mutableState29, state5, (Continuation) null);
                                mutableState30 = mutableState29;
                                composer2.updateRememberedValue(rq0Var2);
                                objRememberedValue43 = rq0Var2;
                            } else {
                                mr0Var2 = mr0Var;
                                mutableState30 = mutableState29;
                            }
                            EffectsKt.LaunchedEffect(objArr6, (Function2) objRememberedValue43, composer2, 0);
                            zChanged7 = composer2.changed((Set) stateCollectAsState19.getValue()) | composer2.changed(list3);
                            objRememberedValue44 = composer2.rememberedValue();
                            if (zChanged7 || objRememberedValue44 == companion.getEmpty()) {
                                obj2 = objRememberedValue44;
                                arrayList = new ArrayList();
                                for (Object obj6 : list3) {
                                    if (!((Set) stateCollectAsState19.getValue()).contains(((Library) obj6).getId())) {
                                        arrayList.add(obj6);
                                    }
                                }
                                composer2.updateRememberedValue(arrayList);
                                obj2 = arrayList;
                            }
                            obj2 = objRememberedValue44;
                            List list4 = (List) obj2;
                            zChanged8 = composer2.changed((Set) stateCollectAsState19.getValue()) | composer2.changed(list2);
                            objRememberedValue45 = composer2.rememberedValue();
                            if (zChanged8 || objRememberedValue45 == Composer.Companion.getEmpty()) {
                                obj3 = objRememberedValue45;
                                arrayList2 = new ArrayList();
                                for (Object obj7 : list2) {
                                    if (!((Set) stateCollectAsState19.getValue()).contains(((ez0) obj7).getLibrary().getId())) {
                                        arrayList2.add(obj7);
                                    }
                                }
                                composer2.updateRememberedValue(arrayList2);
                                obj3 = arrayList2;
                            }
                            obj3 = objRememberedValue45;
                            List list5 = (List) obj3;
                            List listB = mr0Var2.b();
                            String str4 = (String) state3.getValue();
                            Integer numA = A(stateCollectAsState9);
                            String strB = B(stateCollectAsState10);
                            String strC4 = C(stateCollectAsState11);
                            boolean zD = D(stateCollectAsState12);
                            boolean zF = F(stateCollectAsState14);
                            if (function5 == null) {
                                composer2.startReplaceGroup(-1689383606);
                                composer2.endReplaceGroup();
                                function10 = null;
                            } else {
                                composer2.startReplaceGroup(-1689383605);
                                zChangedInstance6 = composer2.changedInstance(fs0Var3) | composer2.changed(function5);
                                objRememberedValue46 = composer2.rememberedValue();
                                if (zChangedInstance6 || objRememberedValue46 == Composer.Companion.getEmpty()) {
                                    objRememberedValue46 = new wh(fs0Var3, function5, mutableState30, 5);
                                    composer2.updateRememberedValue(objRememberedValue46);
                                }
                                composer2.endReplaceGroup();
                                function10 = (Function3) objRememberedValue46;
                            }
                            boolean zBooleanValue4 = ((Boolean) mutableState99.getValue()).booleanValue();
                            boolean zBooleanValue5 = ((Boolean) mutableState100.getValue()).booleanValue();
                            ItemCounts itemCounts = (ItemCounts) state5.getValue();
                            int iIntValue = ((Number) mutableState93.getValue()).intValue();
                            boolean zChangedInstance32 = composer2.changedInstance(fs0Var3);
                            i15 = i14 & 14;
                            if (i15 == 4) {
                                z15 = true;
                            } else {
                                z15 = false;
                            }
                            z16 = zChangedInstance32 | z15;
                            objRememberedValue47 = composer2.rememberedValue();
                            if (!z16 || objRememberedValue47 == Composer.Companion.getEmpty()) {
                                final int i36 = 0;
                                function11 = function1;
                                objRememberedValue47 = new Function1() { // from class: cq0
                                    public final Object invoke(Object obj8) {
                                        int i37 = i36;
                                        MutableState mutableState117 = mutableState30;
                                        Function1 function18 = function11;
                                        fs0 fs0Var5 = fs0Var3;
                                        String str5 = (String) obj8;
                                        switch (i37) {
                                            case 0:
                                                str5.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, str5, "我的媒体", uo0.b, 12);
                                                function18.invoke(str5);
                                                break;
                                            case 1:
                                                str5.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, str5, null, null, 30);
                                                function18.invoke(str5);
                                                break;
                                            default:
                                                str5.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, str5, null, null, 30);
                                                function18.invoke(str5);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composer2.updateRememberedValue(objRememberedValue47);
                            } else {
                                function11 = function1;
                            }
                            Function1 function18 = (Function1) objRememberedValue47;
                            boolean zChangedInstance33 = composer2.changedInstance(fs0Var3);
                            if (i15 == 4) {
                                z17 = true;
                            } else {
                                z17 = false;
                            }
                            z18 = zChangedInstance33 | z17;
                            objRememberedValue48 = composer2.rememberedValue();
                            if (!z18 || objRememberedValue48 == Composer.Companion.getEmpty()) {
                                i16 = 0;
                                objRememberedValue48 = new Function2() { // from class: dq0
                                    public final Object invoke(Object obj8, Object obj9) {
                                        int i37 = i16;
                                        MutableState mutableState117 = mutableState30;
                                        Function1 function19 = function11;
                                        fs0 fs0Var5 = fs0Var3;
                                        String str5 = (String) obj8;
                                        String str6 = (String) obj9;
                                        switch (i37) {
                                            case 0:
                                                str5.getClass();
                                                str6.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, "header_".concat(str5), str6, uo0.c, 12);
                                                function19.invoke(str5);
                                                break;
                                            default:
                                                str5.getClass();
                                                str6.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, str5, str6, uo0.c, 12);
                                                function19.invoke(str5);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composer2.updateRememberedValue(objRememberedValue48);
                            } else {
                                i16 = 0;
                            }
                            Function2 function19 = (Function2) objRememberedValue48;
                            boolean zChangedInstance34 = composer2.changedInstance(fs0Var3);
                            if (i15 == 4) {
                                i17 = 1;
                            } else {
                                i17 = i16;
                            }
                            i18 = i17 | (zChangedInstance34 ? 1 : 0);
                            objRememberedValue49 = composer2.rememberedValue();
                            if (i18 == 0 || objRememberedValue49 == Composer.Companion.getEmpty()) {
                                objRememberedValue49 = new wh(fs0Var3, function11, mutableState30, 6);
                                composer2.updateRememberedValue(objRememberedValue49);
                            }
                            Function3 function20 = (Function3) objRememberedValue49;
                            boolean zChangedInstance35 = composer2.changedInstance(fs0Var3);
                            i19 = i14 & 112;
                            if (i19 == 32) {
                                i20 = 1;
                            } else {
                                i20 = i16;
                            }
                            i21 = (zChangedInstance35 ? 1 : 0) | i20;
                            objRememberedValue50 = composer2.rememberedValue();
                            if (i21 == 0 || objRememberedValue50 == Composer.Companion.getEmpty()) {
                                final int i37 = 1;
                                objRememberedValue50 = new Function2() { // from class: dq0
                                    public final Object invoke(Object obj8, Object obj9) {
                                        int i38 = i37;
                                        MutableState mutableState117 = mutableState30;
                                        Function1 function110 = function2;
                                        fs0 fs0Var5 = fs0Var3;
                                        String str5 = (String) obj8;
                                        String str6 = (String) obj9;
                                        switch (i38) {
                                            case 0:
                                                str5.getClass();
                                                str6.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, "header_".concat(str5), str6, uo0.c, 12);
                                                function110.invoke(str5);
                                                break;
                                            default:
                                                str5.getClass();
                                                str6.getClass();
                                                Objects.toString((to0) mutableState117.getValue());
                                                fs0Var5.R((to0) mutableState117.getValue());
                                                fs0.Q(fs0Var5, str5, str6, uo0.c, 12);
                                                function110.invoke(str5);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composer2.updateRememberedValue(objRememberedValue50);
                            }
                            Function2 function21 = (Function2) objRememberedValue50;
                            boolean zChangedInstance36 = composer2.changedInstance(fs0Var3);
                            if ((i14 & 896) == 256) {
                                z19 = true;
                            } else {
                                z19 = false;
                            }
                            boolean z49 = z19 | zChangedInstance36;
                            if (i19 == 32) {
                                z20 = true;
                            } else {
                                z20 = false;
                            }
                            z21 = z49 | z20;
                            objRememberedValue51 = composer2.rememberedValue();
                            if (!z21 || objRememberedValue51 == Composer.Companion.getEmpty()) {
                                fs0 fs0Var5 = fs0Var3;
                                mutableState31 = mutableState30;
                                z22 = false;
                                fd fdVar2 = new fd(3, fs0Var5, function3, function2, mutableState31);
                                fs0Var3 = fs0Var5;
                                composer2.updateRememberedValue(fdVar2);
                                objRememberedValue51 = fdVar2;
                            } else {
                                mutableState31 = mutableState30;
                                z22 = false;
                            }
                            Function2 function22 = (Function2) objRememberedValue51;
                            zChangedInstance7 = composer2.changedInstance(fs0Var3);
                            objRememberedValue52 = composer2.rememberedValue();
                            if (zChangedInstance7 || objRememberedValue52 == Composer.Companion.getEmpty()) {
                                final int i38 = 1;
                                objRememberedValue52 = new Function0() { // from class: bq0
                                    public final Object invoke() throws NoSuchAlgorithmException {
                                        int i39 = i38;
                                        fs0 fs0Var6 = fs0Var3;
                                        switch (i39) {
                                            case 0:
                                                fs0Var6.G(false, false);
                                                break;
                                            case 1:
                                                fs0Var6.k.setValue(Boolean.FALSE);
                                                break;
                                            default:
                                                fs0Var6.G(false, false);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composer2.updateRememberedValue(objRememberedValue52);
                            }
                            Function0 function23 = (Function0) objRememberedValue52;
                            objRememberedValue53 = composer2.rememberedValue();
                            companion4 = Composer.Companion;
                            if (objRememberedValue53 == companion4.getEmpty()) {
                                state6 = r79;
                                objRememberedValue53 = new z70(23, state6);
                                composer2.updateRememberedValue(objRememberedValue53);
                            } else {
                                state6 = r79;
                            }
                            Function1 function24 = (Function1) objRememberedValue53;
                            objRememberedValue54 = composer2.rememberedValue();
                            if (objRememberedValue54 == companion4.getEmpty()) {
                                objRememberedValue54 = new z1(state6, mutableState90, mutableState91, 4);
                                composer2.updateRememberedValue(objRememberedValue54);
                            }
                            Function1 function25 = (Function1) objRememberedValue54;
                            objRememberedValue55 = composer2.rememberedValue();
                            if (objRememberedValue55 == companion4.getEmpty()) {
                                objRememberedValue55 = new x50(22, mutableState93);
                                composer2.updateRememberedValue(objRememberedValue55);
                            }
                            state8 = state6;
                            a(z, list4, listB, list5, str4, numA, strB, strC4, zD, zF, function18, function19, function20, function21, function22, function10, function23, fs0Var3, zBooleanValue4, zBooleanValue5, itemCounts, function24, function25, iIntValue, (Function0) objRememberedValue55, composer2, i12, (i31 << 18) & 29360128, 25008);
                            composer2 = composer2;
                            composer2.endReplaceGroup();
                        }
                    }
                    composer2.endReplaceGroup();
                    Unit unit4 = Unit.INSTANCE;
                }
                composer2.endNode();
                strC2 = M(mutableState22).c();
                serverPreferences5 = serverPreferences4;
                if (strC2 != null || (serverById2 = serverPreferences5.getServerById(strC2)) == null || (effectiveServerConfig = serverById2.getEffectiveServerConfig()) == null) {
                    host = str;
                } else {
                    host = effectiveServerConfig.getHost();
                }
                if (N(stateCollectAsState18).getEnabled() || !N(stateCollectAsState18).getBypassLan() || M(mutableState22).c || host == null || !ct1.b(host)) {
                    z23 = z22;
                } else {
                    z23 = true;
                }
                if (N(stateCollectAsState18).getEnabled() || M(mutableState22).c || z23) {
                    z24 = z22;
                } else {
                    z24 = true;
                }
                if (z) {
                    composer2.startReplaceGroup(1337618891);
                    to0 to0VarH4 = H(mutableState31);
                    objRememberedValue121 = composer2.rememberedValue();
                    companion9 = Composer.Companion;
                    if (objRememberedValue121 == companion9.getEmpty()) {
                        objRememberedValue121 = new z70(26, mutableState31);
                        composer2.updateRememberedValue(objRememberedValue121);
                    }
                    Function1 function26 = (Function1) objRememberedValue121;
                    boolean z50 = !((Boolean) mutableState97.getValue()).booleanValue();
                    boolean zBooleanValue6 = ((Boolean) mutableState98.getValue()).booleanValue();
                    String strA2 = M(mutableState22).a();
                    String strB2 = M(mutableState22).b();
                    objRememberedValue122 = composer2.rememberedValue();
                    if (objRememberedValue122 == companion9.getEmpty()) {
                        mutableState33 = mutableState28;
                        objRememberedValue122 = new x50(23, mutableState33);
                        composer2.updateRememberedValue(objRememberedValue122);
                    } else {
                        mutableState33 = mutableState28;
                    }
                    Function0 function27 = (Function0) objRememberedValue122;
                    objRememberedValue123 = composer2.rememberedValue();
                    if (objRememberedValue123 == companion9.getEmpty()) {
                        mutableState = mutableState;
                        mutableState36 = mutableState2;
                        objRememberedValue123 = new yo0(12, mutableState36, mutableState);
                        composer2.updateRememberedValue(objRememberedValue123);
                    } else {
                        mutableState = mutableState;
                        mutableState36 = mutableState2;
                    }
                    Function0 function28 = (Function0) objRememberedValue123;
                    objRememberedValue124 = composer2.rememberedValue();
                    if (objRememberedValue124 == companion9.getEmpty()) {
                        mutableState34 = mutableState108;
                        mutableState35 = mutableState109;
                        objRememberedValue124 = new ep0(mutableState31, mutableState35, mutableState34, 1);
                        composer2.updateRememberedValue(objRememberedValue124);
                    } else {
                        mutableState34 = mutableState108;
                        mutableState35 = mutableState109;
                    }
                    Function0 function29 = (Function0) objRememberedValue124;
                    objRememberedValue125 = composer2.rememberedValue();
                    if (objRememberedValue125 == companion9.getEmpty()) {
                        objRememberedValue125 = new ep0(mutableState31, mutableState35, mutableState34, 2);
                        composer2.updateRememberedValue(objRememberedValue125);
                    }
                    Function0 function30 = (Function0) objRememberedValue125;
                    zChangedInstance27 = composer2.changedInstance(fs0Var3);
                    objRememberedValue126 = composer2.rememberedValue();
                    if (zChangedInstance27 || objRememberedValue126 == companion9.getEmpty()) {
                        final int i39 = 2;
                        objRememberedValue126 = new Function0() { // from class: bq0
                            public final Object invoke() throws NoSuchAlgorithmException {
                                int i310 = i39;
                                fs0 fs0Var6 = fs0Var3;
                                switch (i310) {
                                    case 0:
                                        fs0Var6.G(false, false);
                                        break;
                                    case 1:
                                        fs0Var6.k.setValue(Boolean.FALSE);
                                        break;
                                    default:
                                        fs0Var6.G(false, false);
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composer2.updateRememberedValue(objRememberedValue126);
                    }
                    Function0 function31 = (Function0) objRememberedValue126;
                    boolean zBooleanValue7 = ((Boolean) state8.getValue()).booleanValue();
                    if (N(stateCollectAsState18).getEnabled() || !M(mutableState22).c) {
                        z37 = z22;
                    } else {
                        z37 = true;
                    }
                    if (((Boolean) state4.getValue()).booleanValue()) {
                        bdVar2.getClass();
                        if (((Boolean) xc.b.getValue()).booleanValue()) {
                            z38 = true;
                        } else {
                            z38 = z22;
                        }
                    } else {
                        z38 = z22;
                    }
                    int iIntValue2 = ((Number) mutableState90.getValue()).intValue();
                    int iIntValue3 = ((Number) mutableState91.getValue()).intValue();
                    int iZ = z(mutableState92);
                    objRememberedValue127 = composer2.rememberedValue();
                    if (objRememberedValue127 == companion9.getEmpty()) {
                        objRememberedValue127 = new x50(24, mutableState90);
                        composer2.updateRememberedValue(objRememberedValue127);
                    }
                    Function0 function32 = (Function0) objRememberedValue127;
                    objRememberedValue128 = composer2.rememberedValue();
                    if (objRememberedValue128 == companion9.getEmpty()) {
                        objRememberedValue128 = new x50(25, mutableState91);
                        composer2.updateRememberedValue(objRememberedValue128);
                    }
                    Function0 function33 = (Function0) objRememberedValue128;
                    objRememberedValue129 = composer2.rememberedValue();
                    if (objRememberedValue129 == companion9.getEmpty()) {
                        objRememberedValue129 = new z70(27, mutableState25);
                        composer2.updateRememberedValue(objRememberedValue129);
                    }
                    Composer composer21 = composer2;
                    ya2.d(to0VarH4, function26, z50, zBooleanValue6, strA2, strB2, function27, function28, function29, function30, function31, zBooleanValue7, z24, z37, z23, z38, focusRequester, iIntValue2, iIntValue3, iZ, function32, function33, (Function1) objRememberedValue129, boxScopeInstance.align(Modifier.Companion, Alignment.Companion.getTopCenter()), composer21, 920125488, 1572864, 438);
                    composer2 = composer21;
                    composer2.endReplaceGroup();
                } else {
                    i14 = i14;
                    state5 = state5;
                    mutableState33 = mutableState28;
                    mutableState34 = mutableState108;
                    mutableState35 = mutableState109;
                    mutableState36 = mutableState2;
                    composer2.startReplaceGroup(1339902227);
                    composer2.endReplaceGroup();
                }
                composer2.endNode();
                if (z) {
                    composer2.startReplaceGroup(1418126017);
                    if (((Boolean) mutableState89.getValue()).booleanValue()) {
                        composer2.startReplaceGroup(1417513147);
                        objRememberedValue118 = composer2.rememberedValue();
                        companion8 = Composer.Companion;
                        if (objRememberedValue118 == companion8.getEmpty()) {
                            mutableState85 = mutableState89;
                            objRememberedValue118 = new x50(26, mutableState85);
                            composer2.updateRememberedValue(objRememberedValue118);
                        } else {
                            mutableState85 = mutableState89;
                        }
                        Function0 function34 = (Function0) objRememberedValue118;
                        context4 = context;
                        zChangedInstance26 = composer2.changedInstance(context4);
                        objRememberedValue119 = composer2.rememberedValue();
                        if (zChangedInstance26 || objRememberedValue119 == companion8.getEmpty()) {
                            objRememberedValue119 = new m4(context4, 1);
                            composer2.updateRememberedValue(objRememberedValue119);
                        }
                        Function0 function35 = (Function0) objRememberedValue119;
                        if ((i14 & 3670016) == 1048576) {
                            z36 = true;
                        } else {
                            z36 = z22;
                        }
                        objRememberedValue120 = composer2.rememberedValue();
                        if (z36 || objRememberedValue120 == companion8.getEmpty()) {
                            objRememberedValue120 = new u5(function0, mutableState85, 2);
                            composer2.updateRememberedValue(objRememberedValue120);
                        }
                        Composer composer22 = composer2;
                        context2 = context4;
                        ri0.a(function34, function35, (Function0) objRememberedValue120, "是否退出", "退出软件", "退回服务页", Dp.constructor-impl(400.0f), Dp.constructor-impl(198.0f), Dp.constructor-impl(156.0f), composer22, 115043334, 512);
                        composer2 = composer22;
                        composer2.endReplaceGroup();
                    } else {
                        context2 = context;
                        composer2.startReplaceGroup(1418095513);
                        composer2.endReplaceGroup();
                    }
                    if (((Boolean) mutableState112.getValue()).booleanValue()) {
                        composer2.startReplaceGroup(1418157482);
                        nr0Var2 = (nr0) stateCollectAsState7.getValue();
                        if (nr0Var2 instanceof mr0) {
                            composer2.startReplaceGroup(1418234052);
                            List list6 = ((mr0) nr0Var2).a;
                            Set set = (Set) stateCollectAsState19.getValue();
                            coroutineScope = coroutineScope3;
                            mutableState37 = mutableState22;
                            zChangedInstance25 = composer2.changedInstance(coroutineScope) | composer2.changedInstance(z01Var2) | composer2.changed(mutableState37);
                            objRememberedValue116 = composer2.rememberedValue();
                            if (zChangedInstance25 || objRememberedValue116 == Composer.Companion.getEmpty()) {
                                objRememberedValue116 = new t4(coroutineScope, z01Var2, mutableState37, 6);
                                composer2.updateRememberedValue(objRememberedValue116);
                            }
                            Function2 function36 = (Function2) objRememberedValue116;
                            objRememberedValue117 = composer2.rememberedValue();
                            if (objRememberedValue117 == Composer.Companion.getEmpty()) {
                                mutableState38 = mutableState112;
                                objRememberedValue117 = new x50(27, mutableState38);
                                composer2.updateRememberedValue(objRememberedValue117);
                            } else {
                                mutableState38 = mutableState112;
                            }
                            Composer composer23 = composer2;
                            t01.a(list6, set, function36, (Function0) objRememberedValue117, composer23, 3072);
                            composer2 = composer23;
                            composer2.endReplaceGroup();
                        } else {
                            mutableState37 = mutableState22;
                            mutableState38 = mutableState112;
                            coroutineScope = coroutineScope3;
                            composer2.startReplaceGroup(1418746265);
                            composer2.endReplaceGroup();
                        }
                        composer2.endReplaceGroup();
                    } else {
                        mutableState37 = mutableState22;
                        mutableState38 = mutableState112;
                        coroutineScope = coroutineScope3;
                        composer2.startReplaceGroup(1418756185);
                        composer2.endReplaceGroup();
                    }
                    if (((Boolean) mutableState113.getValue()).booleanValue()) {
                        composer2.startReplaceGroup(1418807149);
                        List listM = m(mutableState115);
                        zChangedInstance23 = composer2.changedInstance(fs0Var3);
                        objRememberedValue113 = composer2.rememberedValue();
                        if (zChangedInstance23 || objRememberedValue113 == Composer.Companion.getEmpty()) {
                            objRememberedValue113 = new r90(fs0Var3);
                            composer2.updateRememberedValue(objRememberedValue113);
                        }
                        Function2 function37 = (KFunction) objRememberedValue113;
                        zChangedInstance24 = composer2.changedInstance(fs0Var3);
                        objRememberedValue114 = composer2.rememberedValue();
                        if (zChangedInstance24 || objRememberedValue114 == Composer.Companion.getEmpty()) {
                            objRememberedValue114 = new cp0(fs0Var3, 2);
                            composer2.updateRememberedValue(objRememberedValue114);
                        }
                        Function1 function38 = (Function1) objRememberedValue114;
                        objRememberedValue115 = composer2.rememberedValue();
                        if (objRememberedValue115 == Composer.Companion.getEmpty()) {
                            mutableState39 = mutableState115;
                            mutableState40 = mutableState113;
                            objRememberedValue115 = new yo0(13, mutableState40, mutableState39);
                            composer2.updateRememberedValue(objRememberedValue115);
                        } else {
                            mutableState39 = mutableState115;
                            mutableState40 = mutableState113;
                        }
                        Composer composer24 = composer2;
                        g01.b(listM, function37, function38, (Function0) objRememberedValue115, composer24, 24576);
                        composer2 = composer24;
                        composer2.endReplaceGroup();
                    } else {
                        mutableState33 = mutableState33;
                        mutableState39 = mutableState115;
                        mutableState40 = mutableState113;
                        composer2.startReplaceGroup(1419249209);
                        composer2.endReplaceGroup();
                    }
                    if (((Boolean) mutableState114.getValue()).booleanValue()) {
                        composer2.startReplaceGroup(1419321873);
                        objRememberedValue112 = composer2.rememberedValue();
                        if (objRememberedValue112 == Composer.Companion.getEmpty()) {
                            mutableState41 = mutableState114;
                            mutableState84 = mutableState116;
                            objRememberedValue112 = new y5(mutableState84, mutableState41, mutableState39, 15);
                            composer2.updateRememberedValue(objRememberedValue112);
                        } else {
                            mutableState41 = mutableState114;
                            mutableState84 = mutableState116;
                        }
                        mutableState42 = mutableState84;
                        i23 = 15;
                        Composer composer25 = composer2;
                        AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue112, st.i(), (Modifier) null, ComposableLambdaKt.rememberComposableLambda(646043598, true, new t4(mutableState84, mutableState41, mutableState39, 5), composer2, 54), (Function2) null, st.d(), st.a(), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer25, 1772598, 0, 16276);
                        composer4 = composer25;
                        composer4.endReplaceGroup();
                    } else {
                        mutableState40 = mutableState40;
                        composer4 = composer2;
                        i23 = 15;
                        mutableState41 = mutableState114;
                        mutableState42 = mutableState116;
                        composer4.startReplaceGroup(1420221369);
                        composer4.endReplaceGroup();
                    }
                    if (((Boolean) mutableState34.getValue()).booleanValue()) {
                        composer4.startReplaceGroup(1420330768);
                        ItemCounts itemCounts2 = (ItemCounts) state5.getValue();
                        boolean zJ = j(mutableState35);
                        objRememberedValue108 = composer4.rememberedValue();
                        companion7 = Composer.Companion;
                        if (objRememberedValue108 == companion7.getEmpty()) {
                            mutableState110 = mutableState110;
                            objRememberedValue108 = new y5(mutableState34, mutableState35, mutableState110, 16);
                            composer4.updateRememberedValue(objRememberedValue108);
                        } else {
                            mutableState110 = mutableState110;
                        }
                        Function0 function39 = (Function0) objRememberedValue108;
                        objRememberedValue109 = composer4.rememberedValue();
                        if (objRememberedValue109 == companion7.getEmpty()) {
                            objRememberedValue109 = new y5(mutableState34, mutableState35, mutableState38, 17);
                            composer4.updateRememberedValue(objRememberedValue109);
                        }
                        Function0 function40 = (Function0) objRememberedValue109;
                        zChangedInstance22 = composer4.changedInstance(coroutineScope) | composer4.changedInstance(fs0Var3) | composer4.changedInstance(context2);
                        objRememberedValue110 = composer4.rememberedValue();
                        if (zChangedInstance22 || objRememberedValue110 == companion7.getEmpty()) {
                            objRememberedValue110 = new d4(coroutineScope, mutableState34, mutableState35, mutableState42, fs0Var3, mutableState41, mutableState39, mutableState40, context2, 3);
                            composer4.updateRememberedValue(objRememberedValue110);
                        }
                        Function0 function41 = (Function0) objRememberedValue110;
                        objRememberedValue111 = composer4.rememberedValue();
                        if (objRememberedValue111 == companion7.getEmpty()) {
                            objRememberedValue111 = new f4(23, mutableState34, mutableState35);
                            composer4.updateRememberedValue(objRememberedValue111);
                        }
                        so0.a(itemCounts2, zJ, function39, function40, function41, (Function0) objRememberedValue111, composer4, 200064);
                        composer4.endReplaceGroup();
                    } else {
                        composer4.startReplaceGroup(1422645817);
                        composer4.endReplaceGroup();
                    }
                    if (((Boolean) mutableState110.getValue()).booleanValue()) {
                        composer4.startReplaceGroup(1422691728);
                        zChanged10 = composer4.changed(mutableState37) | composer4.changedInstance(serverPreferences5) | composer4.changedInstance(context2) | composer4.changedInstance(fs0Var3);
                        objRememberedValue106 = composer4.rememberedValue();
                        if (!zChanged10 || objRememberedValue106 == Composer.Companion.getEmpty()) {
                            MutableState mutableState117 = mutableState37;
                            MutableState mutableState118 = mutableState110;
                            k1 k1Var = new k1(serverPreferences5, context2, fs0Var3, mutableState117, mutableState118, 7);
                            mutableState43 = mutableState117;
                            mutableState83 = mutableState118;
                            composer4.updateRememberedValue(k1Var);
                            objRememberedValue106 = k1Var;
                        } else {
                            mutableState43 = mutableState37;
                            mutableState83 = mutableState110;
                        }
                        Function1 function42 = (Function1) objRememberedValue106;
                        objRememberedValue107 = composer4.rememberedValue();
                        if (objRememberedValue107 == Composer.Companion.getEmpty()) {
                            objRememberedValue107 = new x50(i23, mutableState83);
                            composer4.updateRememberedValue(objRememberedValue107);
                        }
                        Composer composer26 = composer4;
                        so0.d(null, list, true, function42, (Function0) objRememberedValue107, composer26, 24960, 1);
                        composer5 = composer26;
                        composer5.endReplaceGroup();
                    } else {
                        composer5 = composer4;
                        mutableState43 = mutableState37;
                        composer5.startReplaceGroup(1422977145);
                        composer5.endReplaceGroup();
                    }
                    if (((Boolean) mutableState33.getValue()).booleanValue()) {
                        composer5.startReplaceGroup(1423040540);
                        if ((i14 & 3670016) == 1048576) {
                            z32 = true;
                        } else {
                            z32 = z22;
                        }
                        if ((i14 & 29360128) == 8388608) {
                            z33 = true;
                        } else {
                            z33 = z22;
                        }
                        boolean zChangedInstance37 = z33 | z32 | composer5.changedInstance(serverPreferences5) | composer5.changedInstance(context2) | composer5.changed(mutableState43) | composer5.changedInstance(fs0Var3);
                        if ((i14 & 458752) == 131072) {
                            z34 = true;
                        } else {
                            z34 = z22;
                        }
                        z35 = zChangedInstance37 | z34;
                        objRememberedValue104 = composer5.rememberedValue();
                        if (!z35 || objRememberedValue104 == Composer.Companion.getEmpty()) {
                            fs0 fs0Var6 = fs0Var3;
                            Context context6 = context2;
                            MutableState mutableState119 = mutableState33;
                            i24 = 24;
                            i28 = 16;
                            wo0 wo0Var = new wo0(function0, function7, mutableState119, serverPreferences5, context6, mutableState43, fs0Var6, function6, mutableState31, (MutableState) state8);
                            context2 = context6;
                            mutableState82 = mutableState119;
                            fs0Var3 = fs0Var6;
                            composer5.updateRememberedValue(wo0Var);
                            objRememberedValue104 = wo0Var;
                        } else {
                            mutableState82 = mutableState33;
                            i24 = 24;
                            i28 = 16;
                        }
                        Function1 function43 = (Function1) objRememberedValue104;
                        objRememberedValue105 = composer5.rememberedValue();
                        if (objRememberedValue105 == Composer.Companion.getEmpty()) {
                            objRememberedValue105 = new x50(i28, mutableState82);
                            composer5.updateRememberedValue(objRememberedValue105);
                        }
                        Composer composer27 = composer5;
                        so0.d("切换服务器", listA, true, function43, (Function0) objRememberedValue105, composer27, 24966, 0);
                        composer6 = composer27;
                        composer6.endReplaceGroup();
                    } else {
                        coroutineScope = coroutineScope;
                        serverPreferences5 = serverPreferences5;
                        mutableState43 = mutableState43;
                        composer6 = composer5;
                        bdVar2 = bdVar2;
                        vc2Var = vc2Var;
                        mutableState = mutableState;
                        i24 = 24;
                        composer6.startReplaceGroup(1423837209);
                        composer6.endReplaceGroup();
                    }
                    if (((Boolean) mutableState.getValue()).booleanValue()) {
                        composer6.startReplaceGroup(1423961395);
                        objRememberedValue89 = composer6.rememberedValue();
                        companion6 = Composer.Companion;
                        if (objRememberedValue89 == companion6.getEmpty()) {
                            mutableState45 = mutableState;
                            objRememberedValue89 = new f4(i24, mutableState45, mutableState36);
                            composer6.updateRememberedValue(objRememberedValue89);
                        } else {
                            mutableState45 = mutableState;
                        }
                        Function0 function44 = (Function0) objRememberedValue89;
                        objRememberedValue90 = composer6.rememberedValue();
                        if (objRememberedValue90 == companion6.getEmpty()) {
                            mutableState3 = mutableState3;
                            objRememberedValue90 = new y5(mutableState3, mutableState36, mutableState45, 18);
                            composer6.updateRememberedValue(objRememberedValue90);
                        } else {
                            mutableState3 = mutableState3;
                        }
                        Function0 function45 = (Function0) objRememberedValue90;
                        objRememberedValue91 = composer6.rememberedValue();
                        if (objRememberedValue91 == companion6.getEmpty()) {
                            mutableState47 = mutableState5;
                            objRememberedValue91 = new y5(mutableState47, mutableState36, mutableState45, 19);
                            composer6.updateRememberedValue(objRememberedValue91);
                        } else {
                            mutableState47 = mutableState5;
                        }
                        Function0 function46 = (Function0) objRememberedValue91;
                        objRememberedValue92 = composer6.rememberedValue();
                        if (objRememberedValue92 == companion6.getEmpty()) {
                            mutableState79 = mutableState6;
                            objRememberedValue92 = new y5(mutableState79, mutableState36, mutableState45, 20);
                            composer6.updateRememberedValue(objRememberedValue92);
                        } else {
                            mutableState79 = mutableState6;
                        }
                        Function0 function47 = (Function0) objRememberedValue92;
                        objRememberedValue93 = composer6.rememberedValue();
                        if (objRememberedValue93 == companion6.getEmpty()) {
                            mutableState49 = mutableState7;
                            objRememberedValue93 = new y5(mutableState49, mutableState36, mutableState45, 21);
                            composer6.updateRememberedValue(objRememberedValue93);
                        } else {
                            mutableState49 = mutableState7;
                        }
                        Function0 function48 = (Function0) objRememberedValue93;
                        objRememberedValue94 = composer6.rememberedValue();
                        if (objRememberedValue94 == companion6.getEmpty()) {
                            mutableState80 = mutableState4;
                            objRememberedValue94 = new y5(mutableState80, mutableState36, mutableState45, 22);
                            composer6.updateRememberedValue(objRememberedValue94);
                        } else {
                            mutableState80 = mutableState4;
                        }
                        Function0 function49 = (Function0) objRememberedValue94;
                        objRememberedValue95 = composer6.rememberedValue();
                        mutableState46 = mutableState80;
                        if (objRememberedValue95 == companion6.getEmpty()) {
                            mutableState50 = mutableState8;
                            objRememberedValue95 = new y5(mutableState50, mutableState36, mutableState45, 23);
                            composer6.updateRememberedValue(objRememberedValue95);
                        } else {
                            mutableState50 = mutableState8;
                        }
                        Function0 function50 = (Function0) objRememberedValue95;
                        objRememberedValue96 = composer6.rememberedValue();
                        if (objRememberedValue96 == companion6.getEmpty()) {
                            mutableState51 = mutableState9;
                            objRememberedValue96 = new y5(mutableState51, mutableState36, mutableState45, 24);
                            composer6.updateRememberedValue(objRememberedValue96);
                        } else {
                            mutableState51 = mutableState9;
                        }
                        Function0 function51 = (Function0) objRememberedValue96;
                        zChanged9 = composer6.changed(bdVar2) | composer6.changedInstance(context2);
                        objRememberedValue97 = composer6.rememberedValue();
                        if (!zChanged9 || objRememberedValue97 == companion6.getEmpty()) {
                            MutableState mutableState120 = mutableState36;
                            bd bdVar5 = bdVar2;
                            mutableState48 = mutableState79;
                            z5 z5Var = new z5(bdVar5, context2, mutableState120, mutableState45, 1);
                            bdVar4 = bdVar5;
                            mutableState44 = mutableState120;
                            composer6.updateRememberedValue(z5Var);
                            objRememberedValue97 = z5Var;
                        } else {
                            mutableState44 = mutableState36;
                            bdVar4 = bdVar2;
                            mutableState48 = mutableState79;
                        }
                        Function0 function52 = (Function0) objRememberedValue97;
                        objRememberedValue98 = composer6.rememberedValue();
                        if (objRememberedValue98 == companion6.getEmpty()) {
                            mutableState81 = mutableState10;
                            objRememberedValue98 = new y5(mutableState81, mutableState44, mutableState45, 25);
                            composer6.updateRememberedValue(objRememberedValue98);
                        } else {
                            mutableState81 = mutableState10;
                        }
                        Function0 function53 = (Function0) objRememberedValue98;
                        objRememberedValue99 = composer6.rememberedValue();
                        if (objRememberedValue99 == companion6.getEmpty()) {
                            mutableState53 = mutableState11;
                            objRememberedValue99 = new y5(mutableState53, mutableState44, mutableState45, 26);
                            composer6.updateRememberedValue(objRememberedValue99);
                        } else {
                            mutableState53 = mutableState11;
                        }
                        Function0 function54 = (Function0) objRememberedValue99;
                        objRememberedValue100 = composer6.rememberedValue();
                        if (objRememberedValue100 == companion6.getEmpty()) {
                            mutableState54 = mutableState13;
                            objRememberedValue100 = new y5(mutableState54, mutableState44, mutableState45, 27);
                            composer6.updateRememberedValue(objRememberedValue100);
                        } else {
                            mutableState54 = mutableState13;
                        }
                        Function0 function55 = (Function0) objRememberedValue100;
                        objRememberedValue101 = composer6.rememberedValue();
                        if (objRememberedValue101 == companion6.getEmpty()) {
                            mutableState14 = mutableState14;
                            objRememberedValue101 = new y5(mutableState14, mutableState44, mutableState45, 28);
                            composer6.updateRememberedValue(objRememberedValue101);
                        } else {
                            mutableState14 = mutableState14;
                        }
                        Function0 function56 = (Function0) objRememberedValue101;
                        objRememberedValue102 = composer6.rememberedValue();
                        if (objRememberedValue102 == companion6.getEmpty()) {
                            mutableState55 = mutableState15;
                            objRememberedValue102 = new y5(mutableState55, mutableState44, mutableState45, 29);
                            composer6.updateRememberedValue(objRememberedValue102);
                        } else {
                            mutableState55 = mutableState15;
                        }
                        Function0 function57 = (Function0) objRememberedValue102;
                        zChangedInstance21 = composer6.changedInstance(fs0Var3) | composer6.changedInstance(context2);
                        composer13 = composer6;
                        objRememberedValue103 = composer13.rememberedValue();
                        if (!zChangedInstance21 || objRememberedValue103 == companion6.getEmpty()) {
                            context3 = context2;
                            composer14 = composer13;
                            MutableState mutableState121 = mutableState44;
                            fs0 fs0Var7 = fs0Var3;
                            objRememberedValue103 = new x1(fs0Var7, context3, mutableState16, mutableState17, mutableState121, mutableState45);
                            fs0Var3 = fs0Var7;
                            mutableState44 = mutableState121;
                            composer14.updateRememberedValue(objRememberedValue103);
                        } else {
                            context3 = context2;
                            composer14 = composer13;
                        }
                        Composer composer28 = composer14;
                        mutableState52 = mutableState81;
                        bdVar3 = bdVar4;
                        f12.b(function44, function45, function46, function47, function48, function49, function50, function51, false, function52, function53, function54, function55, function56, function57, (Function0) objRememberedValue103, o(mutableState44), composer28, 14380470, 28086);
                        composer7 = composer28;
                        composer7.endReplaceGroup();
                    } else {
                        mutableState44 = mutableState36;
                        bdVar3 = bdVar2;
                        composer7 = composer6;
                        context3 = context2;
                        serverPreferences5 = serverPreferences5;
                        mutableState45 = mutableState;
                        mutableState46 = mutableState4;
                        mutableState47 = mutableState5;
                        mutableState48 = mutableState6;
                        mutableState49 = mutableState7;
                        mutableState50 = mutableState8;
                        mutableState51 = mutableState9;
                        mutableState52 = mutableState10;
                        mutableState53 = mutableState11;
                        mutableState54 = mutableState13;
                        mutableState55 = mutableState15;
                        composer7.startReplaceGroup(1426827097);
                        composer7.endReplaceGroup();
                    }
                    objRememberedValue59 = composer7.rememberedValue();
                    companion5 = Composer.Companion;
                    if (objRememberedValue59 == companion5.getEmpty()) {
                        objRememberedValue59 = new f4(25, mutableState44, mutableState45);
                        composer7.updateRememberedValue(objRememberedValue59);
                    }
                    id.a(bdVar3, (Function0) objRememberedValue59, composer7, 48);
                    if (((Boolean) mutableState3.getValue()).booleanValue()) {
                        composer7.startReplaceGroup(1427129378);
                        objRememberedValue88 = composer7.rememberedValue();
                        if (objRememberedValue88 == companion5.getEmpty()) {
                            objRememberedValue88 = new f4(26, mutableState3, mutableState45);
                            composer7.updateRememberedValue(objRememberedValue88);
                        }
                        Composer composer29 = composer7;
                        v00.a((Function0) objRememberedValue88, true, null, composer29, 54, 4);
                        composer8 = composer29;
                        composer8.endReplaceGroup();
                    } else {
                        composer8 = composer7;
                        composer8.startReplaceGroup(1427305241);
                        composer8.endReplaceGroup();
                    }
                    if (((Boolean) mutableState46.getValue()).booleanValue()) {
                        composer8.startReplaceGroup(1427347277);
                        objRememberedValue87 = composer8.rememberedValue();
                        if (objRememberedValue87 == companion5.getEmpty()) {
                            objRememberedValue87 = new f4(27, mutableState46, mutableState45);
                            composer8.updateRememberedValue(objRememberedValue87);
                        }
                        i25 = 6;
                        ad0.b((Function0) objRememberedValue87, composer8, 6);
                        composer8.endReplaceGroup();
                    } else {
                        i25 = 6;
                        composer8.startReplaceGroup(1427481817);
                        composer8.endReplaceGroup();
                    }
                    if (((Boolean) mutableState52.getValue()).booleanValue()) {
                        composer8.startReplaceGroup(1427541182);
                        objRememberedValue85 = composer8.rememberedValue();
                        if (objRememberedValue85 == companion5.getEmpty()) {
                            objRememberedValue85 = new f4(28, mutableState52, mutableState45);
                            composer8.updateRememberedValue(objRememberedValue85);
                        }
                        Function0 function58 = (Function0) objRememberedValue85;
                        userPreferences = userPreferences2;
                        zChangedInstance20 = composer8.changedInstance(userPreferences) | composer8.changedInstance(context3);
                        objRememberedValue86 = composer8.rememberedValue();
                        if (!zChangedInstance20 || objRememberedValue86 == companion5.getEmpty()) {
                            state7 = state11;
                            objRememberedValue86 = new e4(userPreferences, context3, state7, 1);
                            composer8.updateRememberedValue(objRememberedValue86);
                        } else {
                            state7 = state11;
                        }
                        c61.a(function58, (Function0) objRememberedValue86, composer8, i25);
                        composer8.endReplaceGroup();
                    } else {
                        userPreferences = userPreferences2;
                        state7 = state11;
                        composer8.startReplaceGroup(1428151417);
                        composer8.endReplaceGroup();
                    }
                    if (((Boolean) mutableState48.getValue()).booleanValue()) {
                        composer8.startReplaceGroup(1428194600);
                        objRememberedValue84 = composer8.rememberedValue();
                        if (objRememberedValue84 == companion5.getEmpty()) {
                            objRememberedValue84 = new f4(29, mutableState48, mutableState45);
                            composer8.updateRememberedValue(objRememberedValue84);
                        }
                        r92.f((Function0) objRememberedValue84, composer8, i25);
                        composer8.endReplaceGroup();
                    } else {
                        composer8.startReplaceGroup(1428333945);
                        composer8.endReplaceGroup();
                    }
                    if (((Boolean) mutableState47.getValue()).booleanValue()) {
                        composer8.startReplaceGroup(1428554882);
                        boolean zBooleanValue8 = ((Boolean) mutableState95.getValue()).booleanValue();
                        boolean zBooleanValue9 = ((Boolean) mutableState96.getValue()).booleanValue();
                        boolean zBooleanValue10 = ((Boolean) mutableState97.getValue()).booleanValue();
                        boolean zBooleanValue11 = ((Boolean) mutableState98.getValue()).booleanValue();
                        boolean zBooleanValue12 = ((Boolean) mutableState99.getValue()).booleanValue();
                        boolean zBooleanValue13 = ((Boolean) mutableState100.getValue()).booleanValue();
                        boolean zBooleanValue14 = ((Boolean) state.getValue()).booleanValue();
                        sharedPreferences = xu.e;
                        if (sharedPreferences == null) {
                            z30 = false;
                            z29 = false;
                        } else {
                            z29 = false;
                            z30 = sharedPreferences.getBoolean("per_library_sort_enabled", false);
                        }
                        sharedPreferences2 = xu.e;
                        if (sharedPreferences2 == null) {
                            z31 = z29;
                        } else {
                            z31 = sharedPreferences2.getBoolean("home_follow_library_sort", z29);
                        }
                        boolean zI = I(mutableState101);
                        boolean zBooleanValue15 = ((Boolean) mutableState102.getValue()).booleanValue();
                        boolean zJ2 = J(mutableState103);
                        boolean enableDetailOverviewFocus = userPreferences.getEnableDetailOverviewFocus();
                        boolean enableDetailExternalPlayer = userPreferences.getEnableDetailExternalPlayer();
                        boolean zBooleanValue16 = ((Boolean) mutableState104.getValue()).booleanValue();
                        boolean zBooleanValue17 = ((Boolean) state4.getValue()).booleanValue();
                        boolean zK = K(mutableState105);
                        AggregateResultSortMode aggregateResultSortMode = userPreferences.getAggregateResultSortMode();
                        boolean zBooleanValue18 = ((Boolean) state7.getValue()).booleanValue();
                        zChangedInstance16 = composer8.changedInstance(userPreferences);
                        boolean z51 = z31;
                        objRememberedValue80 = composer8.rememberedValue();
                        if (zChangedInstance16 || objRememberedValue80 == companion5.getEmpty()) {
                            objRememberedValue80 = new i6(userPreferences, 14);
                            composer8.updateRememberedValue(objRememberedValue80);
                        }
                        Function1 function59 = (KFunction) objRememberedValue80;
                        zChangedInstance17 = composer8.changedInstance(userPreferences);
                        objRememberedValue81 = composer8.rememberedValue();
                        if (zChangedInstance17 || objRememberedValue81 == companion5.getEmpty()) {
                            objRememberedValue81 = new i6(userPreferences, 15);
                            composer8.updateRememberedValue(objRememberedValue81);
                        }
                        Function1 function60 = (KFunction) objRememberedValue81;
                        objRememberedValue82 = composer8.rememberedValue();
                        if (objRememberedValue82 == companion5.getEmpty()) {
                            objRememberedValue82 = new yo0(0, mutableState47, mutableState45);
                            composer8.updateRememberedValue(objRememberedValue82);
                        }
                        function12 = (Function0) objRememberedValue82;
                        zChangedInstance18 = composer8.changedInstance(userPreferences) | composer8.changed(mutableState43) | composer8.changedInstance(fs0Var3);
                        mutableState76 = mutableState47;
                        Object objRememberedValue181 = composer8.rememberedValue();
                        if (!zChangedInstance18 || objRememberedValue181 == companion5.getEmpty()) {
                            final fs0 fs0Var8 = fs0Var3;
                            final MutableState mutableState122 = mutableState45;
                            final MutableState mutableState123 = mutableState43;
                            final State state14 = state4;
                            final State state15 = state;
                            obj5 = new Function15() { // from class: zo0
                                public final Object invoke(Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object obj13, Object obj14, Object obj15, Object obj16, Object obj17, Object obj18, Object obj19, Object obj20, Object obj21, Object obj22) throws NoSuchAlgorithmException {
                                    Boolean bool7 = (Boolean) obj8;
                                    boolean zBooleanValue19 = bool7.booleanValue();
                                    Boolean bool8 = (Boolean) obj9;
                                    boolean zBooleanValue20 = bool8.booleanValue();
                                    Boolean bool9 = (Boolean) obj10;
                                    boolean zBooleanValue21 = bool9.booleanValue();
                                    Boolean bool10 = (Boolean) obj11;
                                    boolean zBooleanValue22 = bool10.booleanValue();
                                    Boolean bool11 = (Boolean) obj12;
                                    boolean zBooleanValue23 = bool11.booleanValue();
                                    Boolean bool12 = (Boolean) obj13;
                                    boolean zBooleanValue24 = bool12.booleanValue();
                                    Boolean bool13 = (Boolean) obj14;
                                    boolean zBooleanValue25 = bool13.booleanValue();
                                    boolean zBooleanValue26 = ((Boolean) obj15).booleanValue();
                                    boolean zBooleanValue27 = ((Boolean) obj16).booleanValue();
                                    Boolean bool14 = (Boolean) obj17;
                                    boolean zBooleanValue28 = bool14.booleanValue();
                                    Boolean bool15 = (Boolean) obj18;
                                    boolean zBooleanValue29 = bool15.booleanValue();
                                    Boolean bool16 = (Boolean) obj19;
                                    boolean zBooleanValue30 = bool16.booleanValue();
                                    Boolean bool17 = (Boolean) obj20;
                                    boolean zBooleanValue31 = bool17.booleanValue();
                                    Boolean bool18 = (Boolean) obj21;
                                    boolean zBooleanValue32 = bool18.booleanValue();
                                    Boolean bool19 = (Boolean) obj22;
                                    boolean zBooleanValue33 = bool19.booleanValue();
                                    mutableState95.setValue(bool7);
                                    mutableState96.setValue(bool8);
                                    mutableState97.setValue(bool9);
                                    mutableState98.setValue(bool10);
                                    mutableState99.setValue(bool11);
                                    mutableState100.setValue(bool12);
                                    state15.setValue(bool13);
                                    mutableState101.setValue(bool14);
                                    mutableState102.setValue(bool15);
                                    mutableState103.setValue(bool16);
                                    mutableState104.setValue(bool17);
                                    state14.setValue(bool18);
                                    mutableState105.setValue(bool19);
                                    UserPreferences userPreferences3 = userPreferences;
                                    userPreferences3.saveEnableAggregateSearch(zBooleanValue19);
                                    userPreferences3.saveEnableAggregateSearchInServerSearch(zBooleanValue20);
                                    userPreferences3.saveDisableTopNavFocusRefresh(zBooleanValue21);
                                    userPreferences3.saveEnableTransparentTopNavButtons(zBooleanValue22);
                                    userPreferences3.saveEnableClickableLibrarySectionTitle(zBooleanValue23);
                                    userPreferences3.saveEnableLibraryRowMoreButton(zBooleanValue24);
                                    userPreferences3.saveEnableHomeUnplayedOnly(zBooleanValue25);
                                    MutableState mutableState124 = mutableState123;
                                    String strD = xu.D(((ir0) mutableState124.getValue()).a);
                                    xu.S(zBooleanValue26);
                                    xu.R(zBooleanValue27);
                                    userPreferences3.saveEnablePlaybackStartupPoster(zBooleanValue28);
                                    userPreferences3.saveEnableAggregateSearchVersionTags(zBooleanValue29);
                                    userPreferences3.saveEnableDetailAggregateVersionRail(zBooleanValue30);
                                    userPreferences3.saveEnableHomeBackFocusServerButton(zBooleanValue31);
                                    userPreferences3.saveEnableAppUpdateCheckOnStartup(zBooleanValue32);
                                    userPreferences3.saveEnableSpecialFeatures(zBooleanValue33);
                                    if (!Intrinsics.areEqual(strD, xu.D(((ir0) mutableState124.getValue()).a))) {
                                        fs0Var8.G(false, false);
                                    }
                                    mutableState76.setValue(Boolean.FALSE);
                                    Unit unit5 = Unit.INSTANCE;
                                    mutableState122.setValue(Boolean.TRUE);
                                    return Unit.INSTANCE;
                                }
                            };
                            mutableState58 = mutableState95;
                            mutableState59 = mutableState96;
                            mutableState62 = mutableState99;
                            mutableState63 = mutableState100;
                            mutableState64 = mutableState101;
                            mutableState65 = mutableState102;
                            mutableState66 = mutableState103;
                            mutableState67 = mutableState105;
                            mutableState77 = mutableState123;
                            mutableState78 = mutableState122;
                            mutableState60 = mutableState97;
                            mutableState61 = mutableState98;
                            composer8.updateRememberedValue(obj5);
                        } else {
                            obj5 = objRememberedValue181;
                            mutableState78 = mutableState45;
                            mutableState77 = mutableState43;
                            mutableState58 = mutableState95;
                            mutableState59 = mutableState96;
                            mutableState60 = mutableState97;
                            mutableState61 = mutableState98;
                            mutableState62 = mutableState99;
                            mutableState63 = mutableState100;
                            mutableState64 = mutableState101;
                            mutableState65 = mutableState102;
                            mutableState66 = mutableState103;
                            mutableState67 = mutableState105;
                        }
                        Function15 function110 = (Function15) obj5;
                        Function1 function61 = function59;
                        Function1 function62 = function60;
                        zChangedInstance19 = composer8.changedInstance(userPreferences);
                        objRememberedValue83 = composer8.rememberedValue();
                        if (zChangedInstance19) {
                            function13 = function12;
                        } else {
                            function13 = function12;
                            if (objRememberedValue83 == companion5.getEmpty()) {
                            }
                            Function1 function63 = (Function1) objRememberedValue83;
                            Composer composer30 = composer8;
                            mutableState57 = mutableState77;
                            mutableState56 = mutableState78;
                            ew0.d(zBooleanValue8, zBooleanValue9, zBooleanValue10, zBooleanValue11, zBooleanValue12, zBooleanValue13, zBooleanValue14, z30, z51, zI, zBooleanValue15, zJ2, enableDetailOverviewFocus, enableDetailExternalPlayer, zBooleanValue16, zBooleanValue17, zK, aggregateResultSortMode, zBooleanValue18, function13, function110, function61, function62, function63, composer30, 0, 805306368, 0);
                            composer9 = composer30;
                            composer9.endReplaceGroup();
                        }
                        objRememberedValue83 = new ap0(userPreferences, 0);
                        composer8.updateRememberedValue(objRememberedValue83);
                        Function1 function64 = (Function1) objRememberedValue83;
                        Composer composer31 = composer8;
                        mutableState57 = mutableState77;
                        mutableState56 = mutableState78;
                        ew0.d(zBooleanValue8, zBooleanValue9, zBooleanValue10, zBooleanValue11, zBooleanValue12, zBooleanValue13, zBooleanValue14, z30, z51, zI, zBooleanValue15, zJ2, enableDetailOverviewFocus, enableDetailExternalPlayer, zBooleanValue16, zBooleanValue17, zK, aggregateResultSortMode, zBooleanValue18, function13, function110, function61, function62, function64, composer31, 0, 805306368, 0);
                        composer9 = composer31;
                        composer9.endReplaceGroup();
                    } else {
                        composer9 = composer8;
                        mutableState56 = mutableState45;
                        mutableState57 = mutableState43;
                        mutableState55 = mutableState55;
                        userPreferences = userPreferences;
                        mutableState58 = mutableState95;
                        mutableState59 = mutableState96;
                        mutableState60 = mutableState97;
                        mutableState61 = mutableState98;
                        mutableState62 = mutableState99;
                        mutableState63 = mutableState100;
                        mutableState64 = mutableState101;
                        mutableState65 = mutableState102;
                        mutableState66 = mutableState103;
                        mutableState67 = mutableState105;
                        composer9.startReplaceGroup(1433660985);
                        composer9.endReplaceGroup();
                    }
                    if (((Boolean) mutableState49.getValue()).booleanValue()) {
                        composer9.startReplaceGroup(1433717436);
                        String qrNetworkInterfaceName = userPreferences.getQrNetworkInterfaceName();
                        objRememberedValue78 = composer9.rememberedValue();
                        if (objRememberedValue78 == companion5.getEmpty()) {
                            objRememberedValue78 = new yo0(1, mutableState49, mutableState56);
                            composer9.updateRememberedValue(objRememberedValue78);
                        }
                        Function0 function65 = (Function0) objRememberedValue78;
                        zChangedInstance15 = composer9.changedInstance(userPreferences);
                        objRememberedValue79 = composer9.rememberedValue();
                        if (!zChangedInstance15 || objRememberedValue79 == companion5.getEmpty()) {
                            z25 = false;
                            objRememberedValue79 = new bp0(userPreferences, mutableState49, mutableState56, 0);
                            composer9.updateRememberedValue(objRememberedValue79);
                        } else {
                            z25 = false;
                        }
                        ot1.b(qrNetworkInterfaceName, function65, (Function1) objRememberedValue79, composer9, 48);
                        composer9.endReplaceGroup();
                    } else {
                        z25 = false;
                        composer9.startReplaceGroup(1434145081);
                        composer9.endReplaceGroup();
                    }
                    if (((Boolean) mutableState51.getValue()).booleanValue()) {
                        composer9.startReplaceGroup(1434189318);
                        objRememberedValue77 = composer9.rememberedValue();
                        if (objRememberedValue77 == companion5.getEmpty()) {
                            objRememberedValue77 = new yo0(2, mutableState51, mutableState56);
                            composer9.updateRememberedValue(objRememberedValue77);
                        }
                        an.a((Function0) objRememberedValue77, composer9, 6);
                        composer9.endReplaceGroup();
                    } else {
                        composer9.startReplaceGroup(1434330585);
                        composer9.endReplaceGroup();
                    }
                    if (((Boolean) mutableState53.getValue()).booleanValue()) {
                        composer9.startReplaceGroup(1434388214);
                        List listR = r(stateCollectAsState);
                        String strS = s(stateCollectAsState2);
                        objRememberedValue73 = composer9.rememberedValue();
                        if (objRememberedValue73 == companion5.getEmpty()) {
                            mutableState68 = mutableState53;
                            mutableState69 = mutableState27;
                            objRememberedValue73 = new yo0(3, mutableState69, mutableState68);
                            composer9.updateRememberedValue(objRememberedValue73);
                        } else {
                            mutableState68 = mutableState53;
                            mutableState69 = mutableState27;
                        }
                        Function0 function66 = (Function0) objRememberedValue73;
                        coroutineScope2 = coroutineScope;
                        v52Var3 = v52Var2;
                        zChangedInstance13 = composer9.changedInstance(coroutineScope2) | composer9.changedInstance(v52Var3);
                        objRememberedValue74 = composer9.rememberedValue();
                        if (zChangedInstance13 || objRememberedValue74 == companion5.getEmpty()) {
                            objRememberedValue74 = new i4(coroutineScope2, v52Var3, 2);
                            composer9.updateRememberedValue(objRememberedValue74);
                        }
                        Function1 function67 = (Function1) objRememberedValue74;
                        zChangedInstance14 = composer9.changedInstance(coroutineScope2) | composer9.changedInstance(v52Var3);
                        objRememberedValue75 = composer9.rememberedValue();
                        if (zChangedInstance14 || objRememberedValue75 == companion5.getEmpty()) {
                            objRememberedValue75 = new i4(coroutineScope2, v52Var3, 3);
                            composer9.updateRememberedValue(objRememberedValue75);
                        }
                        Function1 function68 = (Function1) objRememberedValue75;
                        objRememberedValue76 = composer9.rememberedValue();
                        if (objRememberedValue76 == companion5.getEmpty()) {
                            objRememberedValue76 = new yo0(4, mutableState68, mutableState56);
                            composer9.updateRememberedValue(objRememberedValue76);
                        }
                        Composer composer32 = composer9;
                        z26 = false;
                        n52.d(listR, strS, function66, function67, function68, (Function0) objRememberedValue76, composer32, 196992);
                        composer10 = composer32;
                        composer10.endReplaceGroup();
                    } else {
                        composer10 = composer9;
                        z26 = z25;
                        mutableState68 = mutableState53;
                        mutableState69 = mutableState27;
                        coroutineScope2 = coroutineScope;
                        composer10.startReplaceGroup(1435036889);
                        composer10.endReplaceGroup();
                    }
                    if (((Boolean) mutableState50.getValue()).booleanValue()) {
                        composer10.startReplaceGroup(1435085063);
                        ProxyConfig proxyConfigN3 = N(stateCollectAsState18);
                        objRememberedValue72 = composer10.rememberedValue();
                        if (objRememberedValue72 == companion5.getEmpty()) {
                            mutableState74 = mutableState50;
                            objRememberedValue72 = new yo0(5, mutableState74, mutableState56);
                            composer10.updateRememberedValue(objRememberedValue72);
                        } else {
                            mutableState74 = mutableState50;
                        }
                        Function0 function69 = (Function0) objRememberedValue72;
                        mutableState75 = mutableState56;
                        zChangedInstance12 = composer10.changedInstance(coroutineScope2) | composer10.changedInstance(proxySettings) | composer10.changedInstance(context3);
                        Object objRememberedValue182 = composer10.rememberedValue();
                        if (!zChangedInstance12 || objRememberedValue182 == companion5.getEmpty()) {
                            j4Var = new j4(coroutineScope2, proxySettings, context3, mutableState74, mutableState75, 1);
                            mutableState56 = mutableState75;
                            composer10.updateRememberedValue(j4Var);
                        } else {
                            j4Var = objRememberedValue182;
                            mutableState56 = mutableState75;
                        }
                        Composer composer33 = composer10;
                        xs1.c(proxyConfigN3, proxyConfigServerManager, function69, (Function1) j4Var, composer33, 384);
                        composer11 = composer33;
                        composer11.endReplaceGroup();
                    } else {
                        composer11 = composer10;
                        composer11.startReplaceGroup(1435625145);
                        composer11.endReplaceGroup();
                    }
                    if (((Boolean) mutableState69.getValue()).booleanValue()) {
                        composer11.startReplaceGroup(1435670560);
                        String str5 = (String) mutableState18.getValue();
                        String str6 = (String) mutableState18.getValue();
                        boolean z52 = ((str6 != null || StringsKt.isBlank(str6)) && !((Boolean) mutableState19.getValue()).booleanValue()) ? true : z26;
                        boolean zBooleanValue19 = ((Boolean) mutableState19.getValue()).booleanValue();
                        objRememberedValue71 = composer11.rememberedValue();
                        if (objRememberedValue71 == companion5.getEmpty()) {
                            objRememberedValue71 = new yo0(6, mutableState69, mutableState68);
                            composer11.updateRememberedValue(objRememberedValue71);
                        }
                        Composer composer34 = composer11;
                        a62.a(str5, z52, zBooleanValue19, (Function0) objRememberedValue71, composer34, 3072);
                        composer11 = composer34;
                        composer11.endReplaceGroup();
                    } else {
                        composer11.startReplaceGroup(1435971353);
                        composer11.endReplaceGroup();
                    }
                    if (((Boolean) mutableState54.getValue()).booleanValue()) {
                        composer11.startReplaceGroup(1436031865);
                        fb1 fb1VarV = v(stateCollectAsState3);
                        objRememberedValue69 = composer11.rememberedValue();
                        if (objRememberedValue69 == companion5.getEmpty()) {
                            mutableState73 = mutableState54;
                            objRememberedValue69 = new yo0(7, mutableState73, mutableState56);
                            composer11.updateRememberedValue(objRememberedValue69);
                        } else {
                            mutableState73 = mutableState54;
                        }
                        Function0 function70 = (Function0) objRememberedValue69;
                        zChangedInstance11 = composer11.changedInstance(cc1Var);
                        objRememberedValue70 = composer11.rememberedValue();
                        if (zChangedInstance11 || objRememberedValue70 == companion5.getEmpty()) {
                            objRememberedValue70 = new k4(cc1Var, mutableState73, mutableState56, 1);
                            composer11.updateRememberedValue(objRememberedValue70);
                        }
                        Composer composer35 = composer11;
                        kb1.c(fb1VarV, onlineSubtitleConfigServerManager, function70, (Function1) objRememberedValue70, composer35, 384);
                        composer12 = composer35;
                        composer12.endReplaceGroup();
                    } else {
                        composer12 = composer11;
                        composer12.startReplaceGroup(1436493145);
                        composer12.endReplaceGroup();
                    }
                    if (((Boolean) mutableState14.getValue()).booleanValue()) {
                        composer12.startReplaceGroup(1436544171);
                        ug2 ug2VarW = w(stateCollectAsState4);
                        objRememberedValue66 = composer12.rememberedValue();
                        if (objRememberedValue66 == companion5.getEmpty()) {
                            objRememberedValue66 = new yo0(8, mutableState14, mutableState56);
                            composer12.updateRememberedValue(objRememberedValue66);
                        }
                        Function0 function71 = (Function0) objRememberedValue66;
                        zChangedInstance10 = composer12.changedInstance(oh2Var);
                        objRememberedValue67 = composer12.rememberedValue();
                        if (zChangedInstance10 || objRememberedValue67 == companion5.getEmpty()) {
                            objRememberedValue67 = new l4(oh2Var, 1);
                            composer12.updateRememberedValue(objRememberedValue67);
                        }
                        Function1 function72 = (Function1) objRememberedValue67;
                        serverPreferences6 = serverPreferences5;
                        boolean zChangedInstance38 = composer12.changedInstance(userPreferences) | composer12.changedInstance(serverPreferences6) | composer12.changed(mutableState57) | composer12.changedInstance(context3);
                        if ((i14 & 3670016) == 1048576) {
                            z27 = true;
                        } else {
                            z27 = z26;
                        }
                        boolean zChangedInstance39 = zChangedInstance38 | z27 | composer12.changedInstance(fs0Var3);
                        if ((i14 & 458752) == 131072) {
                            z26 = true;
                        }
                        z28 = zChangedInstance39 | z26;
                        objRememberedValue68 = composer12.rememberedValue();
                        if (z28 || objRememberedValue68 == companion5.getEmpty()) {
                            tq0 tq0Var = new tq0(serverPreferences6, context3, function0, fs0Var3, userPreferences, mutableState58, mutableState59, mutableState60, mutableState61, mutableState62, mutableState63, state, mutableState64, mutableState65, mutableState66, mutableState104, state4, state7, mutableState67, mutableState57, function6, mutableState31, state8);
                            composer12.updateRememberedValue(tq0Var);
                            objRememberedValue68 = tq0Var;
                        }
                        Composer composer36 = composer12;
                        ah2.f(ug2VarW, nh2Var, webDavSyncConfigServerManager, function71, function72, (KFunction) objRememberedValue68, composer36, 3072);
                        composer2 = composer36;
                        composer2.endReplaceGroup();
                    } else {
                        composer2 = composer12;
                        mutableState56 = mutableState56;
                        composer2.startReplaceGroup(1437018905);
                        composer2.endReplaceGroup();
                    }
                    if (((Boolean) mutableState55.getValue()).booleanValue()) {
                        composer2.startReplaceGroup(1437064258);
                        db2 db2VarX = x(stateCollectAsState5);
                        objRememberedValue64 = composer2.rememberedValue();
                        if (objRememberedValue64 == companion5.getEmpty()) {
                            objRememberedValue64 = new yo0(9, mutableState55, mutableState56);
                            composer2.updateRememberedValue(objRememberedValue64);
                        }
                        Function0 function73 = (Function0) objRememberedValue64;
                        vc2Var2 = vc2Var;
                        zChangedInstance9 = composer2.changedInstance(vc2Var2);
                        objRememberedValue65 = composer2.rememberedValue();
                        if (zChangedInstance9 || objRememberedValue65 == companion5.getEmpty()) {
                            objRememberedValue65 = new n4(vc2Var2, 1);
                            composer2.updateRememberedValue(objRememberedValue65);
                        }
                        Composer composer37 = composer2;
                        cd2.b(db2VarX, sc2Var, function73, (Function1) objRememberedValue65, composer37, 384);
                        composer2 = composer37;
                        composer2.endReplaceGroup();
                    } else {
                        composer2.startReplaceGroup(1437393881);
                        composer2.endReplaceGroup();
                    }
                    if (((Boolean) mutableState16.getValue()).booleanValue()) {
                        composer2.startReplaceGroup(1437433840);
                        objRememberedValue63 = composer2.rememberedValue();
                        if (objRememberedValue63 == companion5.getEmpty()) {
                            objRememberedValue63 = new hp0(10);
                            composer2.updateRememberedValue(objRememberedValue63);
                        }
                        Composer composer38 = composer2;
                        AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue63, st.g(), (Modifier) null, (Function2) null, (Function2) null, st.c(), st.e(), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer38, 1769526, 0, 16284);
                        composer2 = composer38;
                        composer2.endReplaceGroup();
                    } else {
                        composer2.startReplaceGroup(1437873017);
                        composer2.endReplaceGroup();
                    }
                    if (((Boolean) mutableState17.getValue()).booleanValue()) {
                        composer2.startReplaceGroup(1437925314);
                        objRememberedValue62 = composer2.rememberedValue();
                        if (objRememberedValue62 == companion5.getEmpty()) {
                            mutableState72 = mutableState17;
                            i26 = 11;
                            objRememberedValue62 = new yo0(11, mutableState72, mutableState56);
                            composer2.updateRememberedValue(objRememberedValue62);
                        } else {
                            mutableState72 = mutableState17;
                            i26 = 11;
                        }
                        i27 = 54;
                        Composer composer39 = composer2;
                        AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue62, ComposableLambdaKt.rememberComposableLambda(2080795661, true, new o4(2, mutableState72, mutableState56), composer2, 54), (Modifier) null, (Function2) null, (Function2) null, st.j(), st.f(), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer39, 1769526, 0, 16284);
                        composer2 = composer39;
                        composer2.endReplaceGroup();
                    } else {
                        i26 = 11;
                        i27 = 54;
                        composer2.startReplaceGroup(1438347193);
                        composer2.endReplaceGroup();
                    }
                    if (t(mutableState20) != null) {
                        composer2.startReplaceGroup(1438399180);
                        objRememberedValue61 = composer2.rememberedValue();
                        if (objRememberedValue61 == companion5.getEmpty()) {
                            mutableState71 = mutableState20;
                            objRememberedValue61 = new x50(20, mutableState71);
                            composer2.updateRememberedValue(objRememberedValue61);
                        } else {
                            mutableState71 = mutableState20;
                        }
                        Composer composer40 = composer2;
                        AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue61, ComposableLambdaKt.rememberComposableLambda(-1287416882, true, new c2(10, mutableState71), composer2, i27), (Modifier) null, (Function2) null, (Function2) null, st.h(), ComposableLambdaKt.rememberComposableLambda(1035531219, true, new c2(i26, mutableState71), composer2, i27), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer40, 1769526, 0, 16284);
                        composer2 = composer40;
                        composer2.endReplaceGroup();
                    } else {
                        composer2.startReplaceGroup(1438780697);
                        composer2.endReplaceGroup();
                    }
                    if (u(mutableState21) != null) {
                        composer2.startReplaceGroup(1438831537);
                        objRememberedValue60 = composer2.rememberedValue();
                        if (objRememberedValue60 == companion5.getEmpty()) {
                            mutableState70 = mutableState21;
                            objRememberedValue60 = new x50(21, mutableState70);
                            composer2.updateRememberedValue(objRememberedValue60);
                        } else {
                            mutableState70 = mutableState21;
                        }
                        Composer composer41 = composer2;
                        AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue60, ComposableLambdaKt.rememberComposableLambda(-360662129, true, new c2(12, mutableState70), composer2, i27), (Modifier) null, (Function2) null, (Function2) null, st.b(), ComposableLambdaKt.rememberComposableLambda(1962285972, true, new c2(13, mutableState70), composer2, i27), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer41, 1769526, 0, 16284);
                        composer2 = composer41;
                        composer2.endReplaceGroup();
                    } else {
                        composer2.startReplaceGroup(1439208249);
                        composer2.endReplaceGroup();
                    }
                    composer2.endReplaceGroup();
                } else {
                    composer2.startReplaceGroup(1439214201);
                    composer2.endReplaceGroup();
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
            j6Var = new j6(fs0Var, serverPreferences, mutableState111, (Continuation) null, 5);
            snapshotMutationPolicy3 = null;
            composerStartRestartGroup.updateRememberedValue(j6Var);
            EffectsKt.LaunchedEffect(bool, proxyConfigN, (Function2) j6Var, composerStartRestartGroup, 0);
            objRememberedValue = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue == companion10.getEmpty()) {
                i9 = 2;
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, 2, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
            } else {
                i9 = 2;
            }
            mutableState = (MutableState) objRememberedValue;
            objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue2 == companion10.getEmpty()) {
                objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy3, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            mutableState2 = (MutableState) objRememberedValue2;
            objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue3 == companion10.getEmpty()) {
                objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
            }
            mutableState3 = (MutableState) objRememberedValue3;
            objRememberedValue4 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue4 == companion10.getEmpty()) {
                objRememberedValue4 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue4);
            }
            mutableState4 = (MutableState) objRememberedValue4;
            objRememberedValue5 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue5 == companion10.getEmpty()) {
                objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue5);
            }
            mutableState5 = (MutableState) objRememberedValue5;
            objRememberedValue6 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue6 == companion10.getEmpty()) {
                objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue6);
            }
            mutableState6 = (MutableState) objRememberedValue6;
            objRememberedValue7 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue7 == companion10.getEmpty()) {
                objRememberedValue7 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue7);
            }
            mutableState7 = (MutableState) objRememberedValue7;
            objRememberedValue8 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue8 == companion10.getEmpty()) {
                objRememberedValue8 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue8);
            }
            mutableState8 = (MutableState) objRememberedValue8;
            objRememberedValue9 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue9 == companion10.getEmpty()) {
                objRememberedValue9 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue9);
            }
            mutableState9 = (MutableState) objRememberedValue9;
            objRememberedValue10 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue10 == companion10.getEmpty()) {
                objRememberedValue10 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue10);
            }
            mutableState10 = (MutableState) objRememberedValue10;
            objRememberedValue11 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue11 == companion10.getEmpty()) {
                objRememberedValue11 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue11);
            }
            mutableState11 = (MutableState) objRememberedValue11;
            objRememberedValue12 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue12 == companion10.getEmpty()) {
                objRememberedValue12 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue12);
            }
            mutableState12 = (MutableState) objRememberedValue12;
            objRememberedValue13 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue13 == companion10.getEmpty()) {
                objRememberedValue13 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue13);
            }
            mutableState13 = (MutableState) objRememberedValue13;
            objRememberedValue14 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue14 == companion10.getEmpty()) {
                objRememberedValue14 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue14);
            }
            mutableState14 = (MutableState) objRememberedValue14;
            objRememberedValue15 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue15 == companion10.getEmpty()) {
                objRememberedValue15 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue15);
            }
            mutableState15 = (MutableState) objRememberedValue15;
            objRememberedValue16 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue16 == companion10.getEmpty()) {
                objRememberedValue16 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue16);
            }
            mutableState16 = (MutableState) objRememberedValue16;
            objRememberedValue17 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue17 == companion10.getEmpty()) {
                objRememberedValue17 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy3, i9, snapshotMutationPolicy3);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue17);
            }
            mutableState17 = (MutableState) objRememberedValue17;
            objRememberedValue18 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue18 == companion10.getEmpty()) {
                objRememberedValue18 = new v52(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue18);
            }
            v52Var = (v52) objRememberedValue18;
            serverPreferences2 = serverPreferences;
            stateCollectAsState = SnapshotStateKt.collectAsState(v52Var.d, CollectionsKt.emptyList(), (CoroutineContext) null, composerStartRestartGroup, 48, 2);
            stateCollectAsState2 = SnapshotStateKt.collectAsState(v52Var.e, (Object) null, (CoroutineContext) null, composerStartRestartGroup, 48, 2);
            objRememberedValue19 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue19 == companion10.getEmpty()) {
                objRememberedValue19 = new SubtitleFontUploadServerManager(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue19);
            }
            subtitleFontUploadServerManager = (SubtitleFontUploadServerManager) objRememberedValue19;
            objRememberedValue20 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue20 == companion10.getEmpty()) {
                snapshotMutationPolicy4 = null;
                MutableState mutableStateMutableStateOf$default18 = SnapshotStateKt.mutableStateOf$default((Object) null, (SnapshotMutationPolicy) null, 2, (Object) null);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default18);
                obj = mutableStateMutableStateOf$default18;
            } else {
                obj = objRememberedValue20;
                snapshotMutationPolicy4 = null;
            }
            mutableState18 = (MutableState) obj;
            objRememberedValue21 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue21 == companion10.getEmpty()) {
                MutableState mutableStateMutableStateOf$default19 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, snapshotMutationPolicy4, 2, snapshotMutationPolicy4);
                composerStartRestartGroup.updateRememberedValue(mutableStateMutableStateOf$default19);
                objRememberedValue21 = mutableStateMutableStateOf$default19;
                i10 = 2;
            } else {
                i10 = 2;
            }
            mutableState19 = (MutableState) objRememberedValue21;
            objRememberedValue22 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue22 == companion10.getEmpty()) {
                objRememberedValue22 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy4, snapshotMutationPolicy4, i10, snapshotMutationPolicy4);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue22);
            }
            mutableState20 = (MutableState) objRememberedValue22;
            objRememberedValue23 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue23 == companion10.getEmpty()) {
                objRememberedValue23 = SnapshotStateKt.mutableStateOf$default(snapshotMutationPolicy4, snapshotMutationPolicy4, i10, snapshotMutationPolicy4);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue23);
            }
            mutableState21 = (MutableState) objRememberedValue23;
            objRememberedValue24 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue24 == companion10.getEmpty()) {
                objRememberedValue24 = new cc1(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue24);
            }
            cc1Var = (cc1) objRememberedValue24;
            stateCollectAsState3 = SnapshotStateKt.collectAsState(cc1Var.c, (fb1) cc1Var.b.getValue(), (CoroutineContext) null, composerStartRestartGroup, 0, 2);
            objRememberedValue25 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue25 == companion10.getEmpty()) {
                objRememberedValue25 = new OnlineSubtitleConfigServerManager(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue25);
            }
            onlineSubtitleConfigServerManager = (OnlineSubtitleConfigServerManager) objRememberedValue25;
            objRememberedValue26 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue26 == companion10.getEmpty()) {
                objRememberedValue26 = new oh2(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue26);
            }
            oh2Var = (oh2) objRememberedValue26;
            stateCollectAsState4 = SnapshotStateKt.collectAsState(oh2Var.c, (ug2) oh2Var.b.getValue(), (CoroutineContext) null, composerStartRestartGroup, 0, 2);
            objRememberedValue27 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue27 == companion10.getEmpty()) {
                objRememberedValue27 = new nh2(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue27);
            }
            nh2Var = (nh2) objRememberedValue27;
            objRememberedValue28 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue28 == companion10.getEmpty()) {
                objRememberedValue28 = new WebDavSyncConfigServerManager(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue28);
            }
            webDavSyncConfigServerManager = (WebDavSyncConfigServerManager) objRememberedValue28;
            objRememberedValue29 = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue29 == companion10.getEmpty()) {
                objRememberedValue29 = new vc2(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue29);
            }
            vc2 vc2Var4 = (vc2) objRememberedValue29;
            stateCollectAsState5 = SnapshotStateKt.collectAsState(vc2Var4.b, vc2Var4.a(), (CoroutineContext) null, composerStartRestartGroup, 0, 2);
            objRememberedValue30 = composerStartRestartGroup.rememberedValue();
            vc2Var = vc2Var4;
            if (objRememberedValue30 == companion10.getEmpty()) {
                objRememberedValue30 = new sc2(context5);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue30);
            }
            sc2Var = (sc2) objRememberedValue30;
            bdVarB = id.b(composerStartRestartGroup);
            Boolean bool7 = (Boolean) state10.getValue();
            bool7.getClass();
            zChanged = composerStartRestartGroup.changed(bdVarB);
            objRememberedValue31 = composerStartRestartGroup.rememberedValue();
            if (zChanged) {
                objRememberedValue31 = new l6(bdVarB, state10, null, 1);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue31);
            } else {
                objRememberedValue31 = new l6(bdVarB, state10, null, 1);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue31);
            }
            EffectsKt.LaunchedEffect(bool7, (Function2) objRememberedValue31, composerStartRestartGroup, 0);
            Boolean bool8 = (Boolean) mutableState12.getValue();
            bool8.getClass();
            zChangedInstance = composerStartRestartGroup.changedInstance(subtitleFontUploadServerManager) | composerStartRestartGroup.changedInstance(v52Var);
            objRememberedValue32 = composerStartRestartGroup.rememberedValue();
            if (zChangedInstance) {
                objRememberedValue32 = new n6(subtitleFontUploadServerManager, mutableState12, mutableState18, mutableState19, v52Var, mutableState21, mutableState11, null, 1);
                v52Var2 = v52Var;
                composerStartRestartGroup.updateRememberedValue(objRememberedValue32);
            } else {
                objRememberedValue32 = new n6(subtitleFontUploadServerManager, mutableState12, mutableState18, mutableState19, v52Var, mutableState21, mutableState11, null, 1);
                v52Var2 = v52Var;
                composerStartRestartGroup.updateRememberedValue(objRememberedValue32);
            }
            EffectsKt.LaunchedEffect(bool8, (Function2) objRememberedValue32, composerStartRestartGroup, 0);
            String strC5 = M(mutableState106).c();
            Boolean bool9 = (Boolean) state9.getValue();
            bool9.getClass();
            boolean zChanged13 = composerStartRestartGroup.changed(mutableState106) | composerStartRestartGroup.changedInstance(fs0Var) | composerStartRestartGroup.changedInstance(context5);
            i11 = i31 & 896;
            if (i11 == 256) {
                z2 = true;
            } else {
                z2 = false;
            }
            z3 = zChanged13 | z2;
            objRememberedValue33 = composerStartRestartGroup.rememberedValue();
            if (z3) {
                composer3 = composerStartRestartGroup;
                mutableState22 = mutableState106;
                rq0 rq0Var3 = new rq0(fs0Var, context5, z, mutableState22, (MutableState) state9, (Continuation) null);
                fs0Var2 = fs0Var;
                context = context5;
                z4 = z;
                state = state9;
                composer3.updateRememberedValue(rq0Var3);
                objRememberedValue33 = rq0Var3;
            } else {
                composer3 = composerStartRestartGroup;
                mutableState22 = mutableState106;
                rq0 rq0Var4 = new rq0(fs0Var, context5, z, mutableState22, (MutableState) state9, (Continuation) null);
                fs0Var2 = fs0Var;
                context = context5;
                z4 = z;
                state = state9;
                composer3.updateRememberedValue(rq0Var4);
                objRememberedValue33 = rq0Var4;
            }
            EffectsKt.LaunchedEffect(strC5, bool9, (Function2) objRememberedValue33, composer3, 0);
            Boolean boolValueOf7 = Boolean.valueOf(z4);
            if (i11 == 256) {
                z5 = true;
            } else {
                z5 = false;
            }
            zChangedInstance2 = z5 | composer3.changedInstance(fs0Var2);
            objRememberedValue34 = composer3.rememberedValue();
            if (zChangedInstance2) {
                objRememberedValue34 = new pq0(z4, fs0Var2, null, 4);
                composer3.updateRememberedValue(objRememberedValue34);
            } else {
                objRememberedValue34 = new pq0(z4, fs0Var2, null, 4);
                composer3.updateRememberedValue(objRememberedValue34);
            }
            i12 = (i31 >> 6) & 14;
            EffectsKt.LaunchedEffect(boolValueOf7, (Function2) objRememberedValue34, composer3, i12);
            to0 to0VarH5 = H(mutableState87);
            objRememberedValue35 = composer3.rememberedValue();
            if (objRememberedValue35 == companion10.getEmpty()) {
                i13 = 0;
                objRememberedValue35 = new mq0(mutableState87, null, 0);
                composer3.updateRememberedValue(objRememberedValue35);
            } else {
                i13 = 0;
            }
            EffectsKt.LaunchedEffect(to0VarH5, (Function2) objRememberedValue35, composer3, i13);
            String str7 = (String) stateCollectAsState8.getValue();
            to0VarL = l(stateCollectAsState6);
            zChanged2 = composer3.changed(stateCollectAsState8) | composer3.changed(stateCollectAsState6);
            Object objRememberedValue1710 = composer3.rememberedValue();
            if (zChanged2) {
                to0Var = to0VarL;
                mutableState23 = mutableState87;
                state2 = stateCollectAsState8;
                j6Var2 = new j6((Object) state2, mutableState23, stateCollectAsState6, (Continuation) null, 3);
                composer3.updateRememberedValue(j6Var2);
            } else {
                to0Var = to0VarL;
                mutableState23 = mutableState87;
                state2 = stateCollectAsState8;
                j6Var2 = new j6((Object) state2, mutableState23, stateCollectAsState6, (Continuation) null, 3);
                composer3.updateRememberedValue(j6Var2);
            }
            EffectsKt.LaunchedEffect(str7, to0Var, (Function2) j6Var2, composer3, 0);
            Boolean boolValueOf8 = Boolean.valueOf(z4);
            to0 to0VarH6 = H(mutableState23);
            String str8 = (String) state2.getValue();
            Boolean boolValueOf9 = Boolean.valueOf(D(stateCollectAsState12));
            Boolean bool10 = (Boolean) mutableState107.getValue();
            bool10.getClass();
            Object[] objArr7 = {boolValueOf8, to0VarH6, str8, boolValueOf9, bool10};
            if (i11 == 256) {
                z6 = true;
            } else {
                z6 = false;
            }
            zChanged3 = z6 | composer3.changed(mutableState107) | composer3.changed(state2) | composer3.changed(stateCollectAsState12);
            Object objRememberedValue1711 = composer3.rememberedValue();
            if (zChanged3) {
                State state16 = state2;
                nq0Var = new nq0(z4, mutableState23, mutableState107, state16, stateCollectAsState12, state8, mutableState91, null);
                mutableState24 = mutableState107;
                state2 = state16;
                composer3.updateRememberedValue(nq0Var);
            } else {
                State state17 = state2;
                nq0Var = new nq0(z4, mutableState23, mutableState107, state17, stateCollectAsState12, state8, mutableState91, null);
                mutableState24 = mutableState107;
                state2 = state17;
                composer3.updateRememberedValue(nq0Var);
            }
            EffectsKt.LaunchedEffect(objArr7, (Function2) nq0Var, composer3, 0);
            Object[] objArr8 = {Boolean.valueOf(z), H(mutableState23), Boolean.valueOf(D(stateCollectAsState12)), E(stateCollectAsState13), (String) state2.getValue()};
            if (i11 == 256) {
                z7 = true;
            } else {
                z7 = false;
            }
            zChanged4 = z7 | composer3.changed(stateCollectAsState12) | composer3.changed(state2) | composer3.changed(stateCollectAsState13) | composer3.changed(mutableState24);
            Object objRememberedValue183 = composer3.rememberedValue();
            if (zChanged4) {
                State state18 = state2;
                oq0Var = new oq0(z, mutableState23, stateCollectAsState12, state18, stateCollectAsState13, mutableState24, state8, mutableState91, mutableState90, null);
                z8 = z;
                state3 = state18;
                composer3.updateRememberedValue(oq0Var);
            } else {
                State state19 = state2;
                oq0Var = new oq0(z, mutableState23, stateCollectAsState12, state19, stateCollectAsState13, mutableState24, state8, mutableState91, mutableState90, null);
                z8 = z;
                state3 = state19;
                composer3.updateRememberedValue(oq0Var);
            }
            EffectsKt.LaunchedEffect(objArr8, (Function2) oq0Var, composer3, 0);
            Boolean boolValueOf10 = Boolean.valueOf(z8);
            if (i11 == 256) {
                z9 = true;
            } else {
                z9 = false;
            }
            objRememberedValue36 = composer3.rememberedValue();
            if (z9) {
                mutableState25 = mutableState94;
                continuation = null;
                objRememberedValue36 = new pq0(z8, mutableState25, continuation, 0);
                composer3.updateRememberedValue(objRememberedValue36);
            } else {
                mutableState25 = mutableState94;
                continuation = null;
                objRememberedValue36 = new pq0(z8, mutableState25, continuation, 0);
                composer3.updateRememberedValue(objRememberedValue36);
            }
            EffectsKt.LaunchedEffect(boolValueOf10, (Function2) objRememberedValue36, composer3, i12);
            strC = M(mutableState22).c();
            if (strC != null) {
                serverById = serverPreferences2.getServerById(strC);
            } else {
                serverById = continuation;
            }
            Boolean bool11 = (Boolean) mutableState110.getValue();
            bool11.getClass();
            ProxyConfig proxyConfigN4 = N(stateCollectAsState18);
            zChangedInstance3 = composer3.changedInstance(serverById) | composer3.changedInstance(fs0Var2);
            objRememberedValue37 = composer3.rememberedValue();
            if (zChangedInstance3) {
                serverPreferences3 = serverPreferences2;
                fs0 fs0Var9 = fs0Var2;
                j6 j6Var4 = new j6(serverById, fs0Var9, mutableState110, (Continuation) null, 4);
                fs0Var3 = fs0Var9;
                composer3.updateRememberedValue(j6Var4);
                objRememberedValue37 = j6Var4;
            } else {
                serverPreferences3 = serverPreferences2;
                fs0 fs0Var10 = fs0Var2;
                j6 j6Var5 = new j6(serverById, fs0Var10, mutableState110, (Continuation) null, 4);
                fs0Var3 = fs0Var10;
                composer3.updateRememberedValue(j6Var5);
                objRememberedValue37 = j6Var5;
            }
            Composer composer110 = composer3;
            EffectsKt.LaunchedEffect(bool11, serverById, proxyConfigN4, (Function2) objRememberedValue37, composer110, 0);
            composer2 = composer110;
            Unit unit5 = Unit.INSTANCE;
            zChangedInstance4 = composer2.changedInstance(fs0Var3);
            objRememberedValue38 = composer2.rememberedValue();
            if (zChangedInstance4) {
                objRememberedValue38 = new cp0(fs0Var3, 0);
                composer2.updateRememberedValue(objRememberedValue38);
            } else {
                objRememberedValue38 = new cp0(fs0Var3, 0);
                composer2.updateRememberedValue(objRememberedValue38);
            }
            EffectsKt.DisposableEffect(unit5, (Function1) objRememberedValue38, composer2, 6);
            if (serverById != 0) {
                listCreateListBuilder = CollectionsKt.createListBuilder();
                String displayAddress2 = serverById.getDisplayAddress();
                if (M(mutableState22).e == null) {
                    z46 = true;
                } else {
                    z46 = false;
                }
                listCreateListBuilder.add(new vo0(null, "主线路", displayAddress2, z46, true, null, null, null, (ServerPingState) ((Map) stateCollectAsState17.getValue()).get(null), null, 1504));
                while (it.hasNext()) {
                    BackupRouteConfig backupRouteConfig2 = (BackupRouteConfig) it.next();
                    String id2 = backupRouteConfig2.getId();
                    alias = backupRouteConfig2.getAlias();
                    if (StringsKt.isBlank(alias)) {
                        alias = "未命名线路";
                    }
                    listCreateListBuilder.add(new vo0(id2, alias, backupRouteConfig2.getDisplayAddress(), Intrinsics.areEqual(M(mutableState22).e, backupRouteConfig2.getId()), false, null, null, null, (ServerPingState) ((Map) stateCollectAsState17.getValue()).get(backupRouteConfig2.getId()), null, 1504));
                }
                listEmptyList = CollectionsKt.build(listCreateListBuilder);
            } else {
                listEmptyList = null;
            }
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            list = listEmptyList;
            listA = r02.a(serverPreferences3.getAllServers(), M(mutableState22).c(), ((Boolean) mutableState95.getValue()).booleanValue(), false, G(stateCollectAsState16), "查看、添加或编辑服务器");
            if (z8) {
                z10 = false;
            } else {
                z10 = false;
            }
            objRememberedValue39 = composer2.rememberedValue();
            companion = Composer.Companion;
            if (objRememberedValue39 == companion.getEmpty()) {
                mutableState26 = mutableState111;
                objRememberedValue39 = new Function0() { // from class: dp0
                    public final Object invoke() {
                        MutableState mutableState1110;
                        Number number;
                        MutableState mutableState1111 = mutableState23;
                        to0 to0Var2 = (to0) mutableState1111.getValue();
                        MutableState mutableState1112 = mutableState111;
                        if (((Boolean) mutableState1112.getValue()).booleanValue()) {
                            mutableState1112.setValue(Boolean.FALSE);
                        } else {
                            MutableState mutableState124 = mutableState12;
                            boolean zBooleanValue20 = ((Boolean) mutableState124.getValue()).booleanValue();
                            MutableState mutableState125 = mutableState11;
                            if (zBooleanValue20) {
                                mutableState124.setValue(Boolean.FALSE);
                                Unit unit6 = Unit.INSTANCE;
                                hr0.q(mutableState125, true);
                            } else {
                                MutableState mutableState126 = mutableState20;
                                if (((String) mutableState126.getValue()) != null) {
                                    mutableState126.setValue((Object) null);
                                } else {
                                    MutableState mutableState127 = mutableState21;
                                    if (((String) mutableState127.getValue()) != null) {
                                        mutableState127.setValue((Object) null);
                                    } else {
                                        MutableState mutableState128 = mutableState3;
                                        boolean zBooleanValue21 = ((Boolean) mutableState128.getValue()).booleanValue();
                                        MutableState mutableState129 = mutableState;
                                        if (zBooleanValue21) {
                                            mutableState128.setValue(Boolean.FALSE);
                                            Unit unit7 = Unit.INSTANCE;
                                            hr0.n(mutableState129, true);
                                        } else {
                                            MutableState mutableState1210 = mutableState4;
                                            if (((Boolean) mutableState1210.getValue()).booleanValue()) {
                                                mutableState1210.setValue(Boolean.FALSE);
                                                Unit unit8 = Unit.INSTANCE;
                                                hr0.n(mutableState129, true);
                                            } else {
                                                MutableState mutableState1211 = mutableState5;
                                                if (((Boolean) mutableState1211.getValue()).booleanValue()) {
                                                    mutableState1211.setValue(Boolean.FALSE);
                                                    Unit unit9 = Unit.INSTANCE;
                                                    hr0.n(mutableState129, true);
                                                } else {
                                                    MutableState mutableState1212 = mutableState6;
                                                    if (((Boolean) mutableState1212.getValue()).booleanValue()) {
                                                        mutableState1212.setValue(Boolean.FALSE);
                                                        Unit unit10 = Unit.INSTANCE;
                                                        hr0.n(mutableState129, true);
                                                    } else {
                                                        MutableState mutableState1213 = mutableState7;
                                                        if (((Boolean) mutableState1213.getValue()).booleanValue()) {
                                                            mutableState1213.setValue(Boolean.FALSE);
                                                            Unit unit11 = Unit.INSTANCE;
                                                            hr0.n(mutableState129, true);
                                                        } else {
                                                            MutableState mutableState130 = mutableState8;
                                                            if (((Boolean) mutableState130.getValue()).booleanValue()) {
                                                                mutableState130.setValue(Boolean.FALSE);
                                                                Unit unit12 = Unit.INSTANCE;
                                                                hr0.n(mutableState129, true);
                                                            } else {
                                                                MutableState mutableState131 = mutableState9;
                                                                if (((Boolean) mutableState131.getValue()).booleanValue()) {
                                                                    mutableState131.setValue(Boolean.FALSE);
                                                                    Unit unit13 = Unit.INSTANCE;
                                                                    hr0.n(mutableState129, true);
                                                                } else {
                                                                    MutableState mutableState132 = mutableState10;
                                                                    if (((Boolean) mutableState132.getValue()).booleanValue()) {
                                                                        mutableState132.setValue(Boolean.FALSE);
                                                                        Unit unit14 = Unit.INSTANCE;
                                                                        hr0.n(mutableState129, true);
                                                                    } else if (((Boolean) mutableState125.getValue()).booleanValue()) {
                                                                        mutableState125.setValue(Boolean.FALSE);
                                                                        Unit unit15 = Unit.INSTANCE;
                                                                        hr0.n(mutableState129, true);
                                                                    } else {
                                                                        MutableState mutableState133 = mutableState13;
                                                                        if (((Boolean) mutableState133.getValue()).booleanValue()) {
                                                                            mutableState133.setValue(Boolean.FALSE);
                                                                            Unit unit16 = Unit.INSTANCE;
                                                                            hr0.n(mutableState129, true);
                                                                        } else {
                                                                            MutableState mutableState134 = mutableState14;
                                                                            if (((Boolean) mutableState134.getValue()).booleanValue()) {
                                                                                mutableState134.setValue(Boolean.FALSE);
                                                                                Unit unit17 = Unit.INSTANCE;
                                                                                hr0.n(mutableState129, true);
                                                                            } else {
                                                                                MutableState mutableState135 = mutableState15;
                                                                                if (((Boolean) mutableState135.getValue()).booleanValue()) {
                                                                                    mutableState135.setValue(Boolean.FALSE);
                                                                                    Unit unit18 = Unit.INSTANCE;
                                                                                    hr0.n(mutableState129, true);
                                                                                } else {
                                                                                    MutableState mutableState136 = mutableState17;
                                                                                    if (((Boolean) mutableState136.getValue()).booleanValue()) {
                                                                                        mutableState136.setValue(Boolean.FALSE);
                                                                                        Unit unit19 = Unit.INSTANCE;
                                                                                        hr0.n(mutableState129, true);
                                                                                    } else if (((Boolean) mutableState129.getValue()).booleanValue()) {
                                                                                        mutableState129.setValue(Boolean.FALSE);
                                                                                        mutableState2.setValue((Object) null);
                                                                                    } else if (!((Boolean) mutableState16.getValue()).booleanValue()) {
                                                                                        MutableState mutableState137 = mutableState110;
                                                                                        if (((Boolean) mutableState137.getValue()).booleanValue()) {
                                                                                            mutableState137.setValue(Boolean.FALSE);
                                                                                        } else {
                                                                                            MutableState mutableState138 = mutableState108;
                                                                                            if (((Boolean) mutableState138.getValue()).booleanValue()) {
                                                                                                mutableState138.setValue(Boolean.FALSE);
                                                                                                hr0.k(mutableState109, false);
                                                                                            } else {
                                                                                                MutableState mutableState139 = mutableState112;
                                                                                                if (((Boolean) mutableState139.getValue()).booleanValue()) {
                                                                                                    mutableState139.setValue(Boolean.FALSE);
                                                                                                } else {
                                                                                                    MutableState mutableState140 = mutableState114;
                                                                                                    boolean zBooleanValue22 = ((Boolean) mutableState140.getValue()).booleanValue();
                                                                                                    MutableState mutableState141 = mutableState115;
                                                                                                    if (zBooleanValue22) {
                                                                                                        hr0.h(mutableState116, mutableState140, mutableState141);
                                                                                                    } else {
                                                                                                        MutableState mutableState142 = mutableState113;
                                                                                                        if (((Boolean) mutableState142.getValue()).booleanValue()) {
                                                                                                            mutableState142.setValue(Boolean.FALSE);
                                                                                                            mutableState141.setValue(CollectionsKt.emptyList());
                                                                                                        } else {
                                                                                                            MutableState mutableState143 = mutableState89;
                                                                                                            if (((Boolean) mutableState143.getValue()).booleanValue()) {
                                                                                                                mutableState143.setValue(Boolean.FALSE);
                                                                                                            } else {
                                                                                                                to0 to0Var3 = to0.a;
                                                                                                                MutableState mutableState144 = state8;
                                                                                                                if (to0Var2 != to0Var3) {
                                                                                                                    mutableState1111.setValue(to0Var3);
                                                                                                                    hr0.p(mutableState144, true);
                                                                                                                    MutableState mutableState145 = mutableState91;
                                                                                                                    zr.A((Number) mutableState145.getValue(), 1, mutableState145);
                                                                                                                    mutableState143.setValue(Boolean.FALSE);
                                                                                                                } else if (!((Boolean) mutableState104.getValue()).booleanValue() || ((Boolean) mutableState25.getValue()).booleanValue()) {
                                                                                                                    mutableState143.setValue(Boolean.TRUE);
                                                                                                                } else {
                                                                                                                    if (((Boolean) mutableState144.getValue()).booleanValue()) {
                                                                                                                        mutableState1110 = mutableState90;
                                                                                                                        number = (Number) mutableState1110.getValue();
                                                                                                                    } else {
                                                                                                                        mutableState1110 = mutableState93;
                                                                                                                        number = (Number) mutableState1110.getValue();
                                                                                                                    }
                                                                                                                    zr.A(number, 1, mutableState1110);
                                                                                                                    mutableState143.setValue(Boolean.FALSE);
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                mutableState27 = mutableState12;
                composer2.updateRememberedValue(objRememberedValue39);
            } else {
                mutableState26 = mutableState111;
                mutableState27 = mutableState12;
            }
            bdVar = bdVarB;
            mutableState28 = mutableState26;
            BackHandlerKt.BackHandler(z10, (Function0) objRememberedValue39, composer2, 48, 0);
            Boolean boolValueOf11 = Boolean.valueOf(z8);
            if (i11 == 256) {
                z11 = true;
            } else {
                z11 = false;
            }
            zChangedInstance5 = z11 | composer2.changedInstance(fs0Var3);
            objRememberedValue40 = composer2.rememberedValue();
            if (zChangedInstance5) {
                final int i310 = 0;
                objRememberedValue40 = new Function1() { // from class: yp0
                    public final Object invoke(Object obj8) {
                        int i311 = i310;
                        Object obj9 = fs0Var3;
                        boolean z410 = z8;
                        switch (i311) {
                            case 0:
                                ((DisposableEffectScope) obj8).getClass();
                                return new uq0(z410, (fs0) obj9);
                            default:
                                return SliderKt.b(z410, (SliderState) obj9, (SemanticsPropertyReceiver) obj8);
                        }
                    }
                };
                composer2.updateRememberedValue(objRememberedValue40);
            } else {
                final int i311 = 0;
                objRememberedValue40 = new Function1() { // from class: yp0
                    public final Object invoke(Object obj8) {
                        int i312 = i311;
                        Object obj9 = fs0Var3;
                        boolean z410 = z8;
                        switch (i312) {
                            case 0:
                                ((DisposableEffectScope) obj8).getClass();
                                return new uq0(z410, (fs0) obj9);
                            default:
                                return SliderKt.b(z410, (SliderState) obj9, (SemanticsPropertyReceiver) obj8);
                        }
                    }
                };
                composer2.updateRememberedValue(objRememberedValue40);
            }
            EffectsKt.DisposableEffect(boolValueOf11, (Function1) objRememberedValue40, composer2, i12);
            modifier = Modifier.Companion;
            state4 = state10;
            Modifier modifierFillMaxSize$default4 = SizeKt.fillMaxSize$default(modifier, 0.0f, 1, (Object) null);
            if (i11 == 256) {
                z12 = true;
            } else {
                z12 = false;
            }
            objRememberedValue41 = composer2.rememberedValue();
            if (z12) {
                objRememberedValue41 = new aq0(z8, 0);
                composer2.updateRememberedValue(objRememberedValue41);
            } else {
                objRememberedValue41 = new aq0(z8, 0);
                composer2.updateRememberedValue(objRememberedValue41);
            }
            Modifier modifierGraphicsLayer2 = GraphicsLayerModifierKt.graphicsLayer(modifierFillMaxSize$default4, (Function1) objRememberedValue41);
            if (i11 == 256) {
                z13 = true;
            } else {
                z13 = false;
            }
            zChanged5 = z13 | composer2.changed(stateCollectAsState12) | composer2.changedInstance(fs0Var3);
            objRememberedValue42 = composer2.rememberedValue();
            if (zChanged5) {
                objRememberedValue42 = new qq0(z8, fs0Var3, mutableState23, stateCollectAsState12);
                composer2.updateRememberedValue(objRememberedValue42);
            } else {
                objRememberedValue42 = new qq0(z8, fs0Var3, mutableState23, stateCollectAsState12);
                composer2.updateRememberedValue(objRememberedValue42);
            }
            Modifier modifierOnPreviewKeyEvent2 = KeyInputModifierKt.onPreviewKeyEvent(modifierGraphicsLayer2, (Function1) objRememberedValue42);
            companion2 = Alignment.Companion;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy4 = BoxKt.maybeCachedBoxMeasurePolicy(companion2.getTopStart(), false);
            int iB6 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
            CompositionLocalMap currentCompositionLocalMap6 = composer2.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier6 = ComposedModifierKt.materializeModifier(composer2, modifierOnPreviewKeyEvent2);
            companion3 = ComposeUiNode.Companion;
            constructor = companion3.getConstructor();
            if (!pd.n(composer2.getApplier())) {
                ComposablesKt.invalidApplier();
            }
            composer2.startReusableNode();
            if (composer2.getInserting()) {
                composer2.createNode(constructor);
            } else {
                composer2.useNode();
            }
            Composer composer111 = Updater.constructor-impl(composer2);
            zr.z(companion3, composer111, measurePolicyMaybeCachedBoxMeasurePolicy4, composer111, currentCompositionLocalMap6);
            Updater.set-impl(composer111, modifierMaterializeModifier6, pd.h(companion3, composer111, Integer.valueOf(iB6), composer111));
            boxScopeInstance = BoxScopeInstance.INSTANCE;
            Modifier modifierFocusGroup2 = FocusableKt.focusGroup(FocusRequesterModifierKt.focusRequester(SizeKt.fillMaxSize$default(modifier, 0.0f, 1, (Object) null), focusRequester));
            arrangement = Arrangement.INSTANCE;
            MeasurePolicy measurePolicyColumnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement.getTop(), companion2.getStart(), composer2, 0);
            int iB7 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
            CompositionLocalMap currentCompositionLocalMap7 = composer2.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier7 = ComposedModifierKt.materializeModifier(composer2, modifierFocusGroup2);
            constructor2 = companion3.getConstructor();
            if (!pd.n(composer2.getApplier())) {
                ComposablesKt.invalidApplier();
            }
            composer2.startReusableNode();
            if (composer2.getInserting()) {
                composer2.createNode(constructor2);
            } else {
                composer2.useNode();
            }
            Composer composer112 = Updater.constructor-impl(composer2);
            zr.z(companion3, composer112, measurePolicyColumnMeasurePolicy2, composer112, currentCompositionLocalMap7);
            Updater.set-impl(composer112, modifierMaterializeModifier7, pd.h(companion3, composer112, Integer.valueOf(iB7), composer112));
            ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
            iOrdinal = H(mutableState23).ordinal();
            if (iOrdinal != 0) {
                if (iOrdinal != 1) {
                    serverPreferences7 = serverPreferences3;
                    mutableState86 = mutableState23;
                    bdVar = bdVar;
                    state4 = state4;
                    i14 = i29;
                    composer2.startReplaceGroup(-1687220673);
                    boolean zBooleanValue20 = ((Boolean) mutableState99.getValue()).booleanValue();
                    boolean zBooleanValue21 = ((Boolean) mutableState100.getValue()).booleanValue();
                    String strL2 = L(mutableState88);
                    zChanged11 = composer2.changed(mutableState88);
                    objRememberedValue130 = composer2.rememberedValue();
                    if (zChanged11) {
                        objRememberedValue130 = new z70(24, mutableState88);
                        composer2.updateRememberedValue(objRememberedValue130);
                    } else {
                        objRememberedValue130 = new z70(24, mutableState88);
                        composer2.updateRememberedValue(objRememberedValue130);
                    }
                    Function1 function111 = (Function1) objRememberedValue130;
                    objRememberedValue131 = composer2.rememberedValue();
                    if (objRememberedValue131 == companion.getEmpty()) {
                        objRememberedValue131 = new z70(25, r79);
                        composer2.updateRememberedValue(objRememberedValue131);
                    }
                    Function1 function112 = (Function1) objRememberedValue131;
                    boolean zChangedInstance210 = composer2.changedInstance(fs0Var3);
                    if ((i14 & 112) == 32) {
                        z39 = true;
                    } else {
                        z39 = false;
                    }
                    z40 = zChangedInstance210 | z39;
                    objRememberedValue132 = composer2.rememberedValue();
                    if (z40) {
                        final int i312 = 1;
                        objRememberedValue132 = new Function1() { // from class: cq0
                            public final Object invoke(Object obj8) {
                                int i313 = i312;
                                MutableState mutableState1110 = mutableState86;
                                Function1 function113 = function2;
                                fs0 fs0Var11 = fs0Var3;
                                String str9 = (String) obj8;
                                switch (i313) {
                                    case 0:
                                        str9.getClass();
                                        Objects.toString((to0) mutableState1110.getValue());
                                        fs0Var11.R((to0) mutableState1110.getValue());
                                        fs0.Q(fs0Var11, str9, "我的媒体", uo0.b, 12);
                                        function113.invoke(str9);
                                        break;
                                    case 1:
                                        str9.getClass();
                                        Objects.toString((to0) mutableState1110.getValue());
                                        fs0Var11.R((to0) mutableState1110.getValue());
                                        fs0.Q(fs0Var11, str9, null, null, 30);
                                        function113.invoke(str9);
                                        break;
                                    default:
                                        str9.getClass();
                                        Objects.toString((to0) mutableState1110.getValue());
                                        fs0Var11.R((to0) mutableState1110.getValue());
                                        fs0.Q(fs0Var11, str9, null, null, 30);
                                        function113.invoke(str9);
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composer2.updateRememberedValue(objRememberedValue132);
                    } else {
                        final int i313 = 1;
                        objRememberedValue132 = new Function1() { // from class: cq0
                            public final Object invoke(Object obj8) {
                                int i314 = i313;
                                MutableState mutableState1110 = mutableState86;
                                Function1 function113 = function2;
                                fs0 fs0Var11 = fs0Var3;
                                String str9 = (String) obj8;
                                switch (i314) {
                                    case 0:
                                        str9.getClass();
                                        Objects.toString((to0) mutableState1110.getValue());
                                        fs0Var11.R((to0) mutableState1110.getValue());
                                        fs0.Q(fs0Var11, str9, "我的媒体", uo0.b, 12);
                                        function113.invoke(str9);
                                        break;
                                    case 1:
                                        str9.getClass();
                                        Objects.toString((to0) mutableState1110.getValue());
                                        fs0Var11.R((to0) mutableState1110.getValue());
                                        fs0.Q(fs0Var11, str9, null, null, 30);
                                        function113.invoke(str9);
                                        break;
                                    default:
                                        str9.getClass();
                                        Objects.toString((to0) mutableState1110.getValue());
                                        fs0Var11.R((to0) mutableState1110.getValue());
                                        fs0.Q(fs0Var11, str9, null, null, 30);
                                        function113.invoke(str9);
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composer2.updateRememberedValue(objRememberedValue132);
                    }
                    zk0.b(z, zBooleanValue20, zBooleanValue21, strL2, function111, function112, function3, (Function1) objRememberedValue132, null, composer2, i12 | ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE | ((i14 << 12) & 3670016));
                    composer2 = composer2;
                    composer2.endReplaceGroup();
                    Unit unit6 = Unit.INSTANCE;
                } else {
                    if (iOrdinal == 2) {
                        composer2.startReplaceGroup(360892379);
                        composer2.endReplaceGroup();
                        dv.f();
                        return;
                    }
                    composer2.startReplaceGroup(-1686184064);
                    if (zBooleanValue) {
                        composer2.startReplaceGroup(-1686123273);
                        objRememberedValue134 = composer2.rememberedValue();
                        if (objRememberedValue134 == companion.getEmpty()) {
                            objRememberedValue134 = new hp0(11);
                            composer2.updateRememberedValue(objRememberedValue134);
                        }
                        Function0 function113 = (Function0) objRememberedValue134;
                        boolean zChangedInstance310 = composer2.changedInstance(serverPreferences3) | composer2.changedInstance(fs0Var3);
                        i14 = i29;
                        if ((i14 & 7168) == 2048) {
                            z43 = true;
                        } else {
                            z43 = false;
                        }
                        boolean z410 = zChangedInstance310 | z43;
                        if ((i14 & 112) == 32) {
                            z44 = true;
                        } else {
                            z44 = false;
                        }
                        z45 = z410 | z44;
                        objRememberedValue135 = composer2.rememberedValue();
                        if (z45) {
                            ServerPreferences serverPreferences10 = serverPreferences3;
                            fd fdVar3 = new fd(4, serverPreferences10, fs0Var3, function4, function2);
                            serverPreferences7 = serverPreferences10;
                            composer2.updateRememberedValue(fdVar3);
                            objRememberedValue135 = fdVar3;
                        } else {
                            ServerPreferences serverPreferences11 = serverPreferences3;
                            fd fdVar4 = new fd(4, serverPreferences11, fs0Var3, function4, function2);
                            serverPreferences7 = serverPreferences11;
                            composer2.updateRememberedValue(fdVar4);
                            objRememberedValue135 = fdVar4;
                        }
                        mutableState86 = mutableState23;
                        z8.a(function113, (Function2) objRememberedValue135, ((Boolean) mutableState102.getValue()).booleanValue(), z, false, PaddingKt.padding-qDBjuR0$default(modifier, 0.0f, Dp.constructor-impl(64.0f), 0.0f, 0.0f, 13, (Object) null), null, composer2, ((i31 << 3) & 7168) | 221190);
                        composer2 = composer2;
                        composer2.endReplaceGroup();
                    } else {
                        serverPreferences7 = serverPreferences3;
                        mutableState86 = mutableState23;
                        bdVar = bdVar;
                        state4 = state4;
                        i14 = i29;
                        composer2.startReplaceGroup(-1684589238);
                        boolean zChangedInstance311 = composer2.changedInstance(fs0Var3);
                        if ((i14 & 112) == 32) {
                            z41 = true;
                        } else {
                            z41 = false;
                        }
                        z42 = zChangedInstance311 | z41;
                        objRememberedValue133 = composer2.rememberedValue();
                        if (z42) {
                            final int i314 = 2;
                            objRememberedValue133 = new Function1() { // from class: cq0
                                public final Object invoke(Object obj8) {
                                    int i315 = i314;
                                    MutableState mutableState1110 = mutableState86;
                                    Function1 function114 = function2;
                                    fs0 fs0Var11 = fs0Var3;
                                    String str9 = (String) obj8;
                                    switch (i315) {
                                        case 0:
                                            str9.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str9, "我的媒体", uo0.b, 12);
                                            function114.invoke(str9);
                                            break;
                                        case 1:
                                            str9.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str9, null, null, 30);
                                            function114.invoke(str9);
                                            break;
                                        default:
                                            str9.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str9, null, null, 30);
                                            function114.invoke(str9);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue133);
                        } else {
                            final int i315 = 2;
                            objRememberedValue133 = new Function1() { // from class: cq0
                                public final Object invoke(Object obj8) {
                                    int i316 = i315;
                                    MutableState mutableState1110 = mutableState86;
                                    Function1 function114 = function2;
                                    fs0 fs0Var11 = fs0Var3;
                                    String str9 = (String) obj8;
                                    switch (i316) {
                                        case 0:
                                            str9.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str9, "我的媒体", uo0.b, 12);
                                            function114.invoke(str9);
                                            break;
                                        case 1:
                                            str9.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str9, null, null, 30);
                                            function114.invoke(str9);
                                            break;
                                        default:
                                            str9.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str9, null, null, 30);
                                            function114.invoke(str9);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue133);
                        }
                        lx1.b(z, (Function1) objRememberedValue133, null, composer2, i12);
                        composer2.endReplaceGroup();
                    }
                    composer2.endReplaceGroup();
                    Unit unit7 = Unit.INSTANCE;
                }
                mutableState31 = mutableState86;
                serverPreferences4 = serverPreferences7;
                boxScopeInstance = boxScopeInstance;
                state8 = r79;
                bdVar2 = bdVar;
                state5 = stateCollectAsState15;
                z01Var2 = z01Var2;
                z22 = false;
                str = null;
            } else {
                serverPreferences4 = serverPreferences3;
                mutableState29 = mutableState23;
                state4 = state4;
                state5 = stateCollectAsState15;
                i14 = i29;
                composer2.startReplaceGroup(-1697239098);
                nr0Var = (nr0) stateCollectAsState7.getValue();
                if (nr0Var instanceof lr0) {
                    composer2.startReplaceGroup(-1697475876);
                    Modifier modifierFillMaxSize$default5 = SizeKt.fillMaxSize$default(modifier, 0.0f, 1, (Object) null);
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy5 = BoxKt.maybeCachedBoxMeasurePolicy(companion2.getCenter(), false);
                    int iB8 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
                    CompositionLocalMap currentCompositionLocalMap8 = composer2.getCurrentCompositionLocalMap();
                    Modifier modifierMaterializeModifier8 = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxSize$default5);
                    constructor5 = companion3.getConstructor();
                    if (!pd.n(composer2.getApplier())) {
                        ComposablesKt.invalidApplier();
                    }
                    composer2.startReusableNode();
                    if (composer2.getInserting()) {
                        composer2.createNode(constructor5);
                    } else {
                        composer2.useNode();
                    }
                    Composer composer113 = Updater.constructor-impl(composer2);
                    zr.z(companion3, composer113, measurePolicyMaybeCachedBoxMeasurePolicy5, composer113, currentCompositionLocalMap8);
                    Updater.set-impl(composer113, modifierMaterializeModifier8, pd.h(companion3, composer113, Integer.valueOf(iB8), composer113));
                    ProgressIndicatorKt.CircularProgressIndicator-4lLiAd8((Modifier) null, 0L, 0.0f, 0L, 0, 0.0f, composer2, 0, 63);
                    composer2 = composer2;
                    composer2.endNode();
                    composer2.endReplaceGroup();
                    bdVar2 = bdVar;
                    state8 = r79;
                    mutableState31 = mutableState29;
                    z22 = false;
                    str = null;
                } else {
                    bdVar2 = bdVar;
                    if (nr0Var instanceof kr0) {
                        composer2.startReplaceGroup(-1697072938);
                        objRememberedValue56 = composer2.rememberedValue();
                        if (objRememberedValue56 == companion.getEmpty()) {
                            obj4 = null;
                            objRememberedValue56 = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, (SnapshotMutationPolicy) null, 2, (Object) null);
                            composer2.updateRememberedValue(objRememberedValue56);
                        } else {
                            obj4 = null;
                        }
                        mutableState32 = (MutableState) objRememberedValue56;
                        Modifier modifierFillMaxSize$default6 = SizeKt.fillMaxSize$default(modifier, 0.0f, 1, obj4);
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy6 = BoxKt.maybeCachedBoxMeasurePolicy(companion2.getCenter(), false);
                        int iB9 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
                        CompositionLocalMap currentCompositionLocalMap9 = composer2.getCurrentCompositionLocalMap();
                        Modifier modifierMaterializeModifier9 = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxSize$default6);
                        constructor3 = companion3.getConstructor();
                        if (!pd.n(composer2.getApplier())) {
                            ComposablesKt.invalidApplier();
                        }
                        composer2.startReusableNode();
                        if (composer2.getInserting()) {
                            composer2.createNode(constructor3);
                        } else {
                            composer2.useNode();
                        }
                        Composer composer114 = Updater.constructor-impl(composer2);
                        zr.z(companion3, composer114, measurePolicyMaybeCachedBoxMeasurePolicy6, composer114, currentCompositionLocalMap9);
                        Updater.set-impl(composer114, modifierMaterializeModifier9, pd.h(companion3, composer114, Integer.valueOf(iB9), composer114));
                        MeasurePolicy measurePolicyP2 = zr.p(16.0f, arrangement, companion2.getCenterHorizontally(), composer2, 54);
                        int iB10 = pd.b(ComposablesKt.getCurrentCompositeKeyHashCode(composer2, 0));
                        CompositionLocalMap currentCompositionLocalMap10 = composer2.getCurrentCompositionLocalMap();
                        Modifier modifierMaterializeModifier10 = ComposedModifierKt.materializeModifier(composer2, modifier);
                        constructor4 = companion3.getConstructor();
                        if (!pd.n(composer2.getApplier())) {
                            ComposablesKt.invalidApplier();
                        }
                        composer2.startReusableNode();
                        if (composer2.getInserting()) {
                            composer2.createNode(constructor4);
                        } else {
                            composer2.useNode();
                        }
                        Composer composer210 = Updater.constructor-impl(composer2);
                        zr.z(companion3, composer210, measurePolicyP2, composer210, currentCompositionLocalMap10);
                        Updater.set-impl(composer210, modifierMaterializeModifier10, pd.h(companion3, composer210, Integer.valueOf(iB10), composer210));
                        String strA3 = ((kr0) nr0Var).a();
                        materialTheme = MaterialTheme.INSTANCE;
                        i22 = MaterialTheme.$stable;
                        str = null;
                        TextKt.Text-Nvy7gAk(strA3, (Modifier) null, materialTheme.getColorScheme(composer2, i22).getError-0d7_KjU(), (TextAutoSize) null, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1) null, materialTheme.getTypography(composer2, i22).getBodyLarge(), composer2, 0, 0, 131066);
                        zChangedInstance8 = composer2.changedInstance(fs0Var3);
                        objRememberedValue57 = composer2.rememberedValue();
                        if (zChangedInstance8) {
                            final int i316 = 0;
                            objRememberedValue57 = new Function0() { // from class: bq0
                                public final Object invoke() throws NoSuchAlgorithmException {
                                    int i317 = i316;
                                    fs0 fs0Var11 = fs0Var3;
                                    switch (i317) {
                                        case 0:
                                            fs0Var11.G(false, false);
                                            break;
                                        case 1:
                                            fs0Var11.k.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            fs0Var11.G(false, false);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue57);
                        } else {
                            final int i317 = 0;
                            objRememberedValue57 = new Function0() { // from class: bq0
                                public final Object invoke() throws NoSuchAlgorithmException {
                                    int i318 = i317;
                                    fs0 fs0Var11 = fs0Var3;
                                    switch (i318) {
                                        case 0:
                                            fs0Var11.G(false, false);
                                            break;
                                        case 1:
                                            fs0Var11.k.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            fs0Var11.G(false, false);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue57);
                        }
                        Function0 function114 = (Function0) objRememberedValue57;
                        objRememberedValue58 = composer2.rememberedValue();
                        if (objRememberedValue58 == companion.getEmpty()) {
                            objRememberedValue58 = new z70(22, mutableState32);
                            composer2.updateRememberedValue(objRememberedValue58);
                        }
                        Modifier modifierOnFocusChanged2 = FocusChangedModifierKt.onFocusChanged(modifier, (Function1) objRememberedValue58);
                        if (y(mutableState32)) {
                            composer2.startReplaceGroup(492049091);
                            modifier2 = BorderKt.border-xT4_qwU(modifier, Dp.constructor-impl(3.0f), Color.Companion.getWhite-0d7_KjU(), materialTheme.getShapes(composer2, i22).getMedium());
                            composer2.endReplaceGroup();
                        } else {
                            composer2.startReplaceGroup(492432096);
                            composer2.endReplaceGroup();
                            modifier2 = modifier;
                        }
                        ButtonKt.Button(function114, modifierOnFocusChanged2.then(modifier2), false, (Shape) null, (ButtonColors) null, (ButtonElevation) null, (BorderStroke) null, (PaddingValues) null, (MutableInteractionSource) null, st.k(), composer2, 805306368, 508);
                        composer2.endNode();
                        composer2.endNode();
                        composer2.endReplaceGroup();
                        composer2 = composer2;
                        mutableState31 = mutableState29;
                        z22 = false;
                    } else {
                        str = null;
                        if (nr0Var instanceof mr0) {
                            composer2.startReplaceGroup(360892348);
                            composer2.endReplaceGroup();
                            dv.f();
                            return;
                        }
                        composer2.startReplaceGroup(-1694909231);
                        Boolean boolValueOf12 = Boolean.valueOf(z);
                        to0 to0VarH7 = H(mutableState29);
                        mr0Var = (mr0) nr0Var;
                        list2 = mr0Var.c;
                        list3 = mr0Var.a;
                        Object[] objArr9 = {boolValueOf12, to0VarH7, list3, (ItemCounts) state5.getValue()};
                        if (i11 == 256) {
                            z14 = true;
                        } else {
                            z14 = false;
                        }
                        zChanged6 = z14 | composer2.changed(nr0Var) | composer2.changed(state5) | composer2.changedInstance(fs0Var3);
                        objRememberedValue43 = composer2.rememberedValue();
                        if (zChanged6) {
                            mr0Var2 = mr0Var;
                            rq0 rq0Var5 = new rq0(z, mr0Var2, fs0Var3, mutableState29, state5, (Continuation) null);
                            mutableState30 = mutableState29;
                            composer2.updateRememberedValue(rq0Var5);
                            objRememberedValue43 = rq0Var5;
                        } else {
                            mr0Var2 = mr0Var;
                            rq0 rq0Var6 = new rq0(z, mr0Var2, fs0Var3, mutableState29, state5, (Continuation) null);
                            mutableState30 = mutableState29;
                            composer2.updateRememberedValue(rq0Var6);
                            objRememberedValue43 = rq0Var6;
                        }
                        EffectsKt.LaunchedEffect(objArr9, (Function2) objRememberedValue43, composer2, 0);
                        zChanged7 = composer2.changed((Set) stateCollectAsState19.getValue()) | composer2.changed(list3);
                        objRememberedValue44 = composer2.rememberedValue();
                        if (zChanged7) {
                            obj2 = objRememberedValue44;
                            arrayList = new ArrayList();
                            while (r2.hasNext()) {
                                if (!((Set) stateCollectAsState19.getValue()).contains(((Library) obj6).getId())) {
                                    arrayList.add(obj6);
                                }
                            }
                            composer2.updateRememberedValue(arrayList);
                            obj2 = arrayList;
                        } else {
                            obj2 = objRememberedValue44;
                            arrayList = new ArrayList();
                            while (r2.hasNext()) {
                                if (!((Set) stateCollectAsState19.getValue()).contains(((Library) obj6).getId())) {
                                    arrayList.add(obj6);
                                }
                            }
                            composer2.updateRememberedValue(arrayList);
                            obj2 = arrayList;
                        }
                        obj2 = objRememberedValue44;
                        List list7 = (List) obj2;
                        zChanged8 = composer2.changed((Set) stateCollectAsState19.getValue()) | composer2.changed(list2);
                        objRememberedValue45 = composer2.rememberedValue();
                        if (zChanged8) {
                            obj3 = objRememberedValue45;
                            arrayList2 = new ArrayList();
                            while (r2.hasNext()) {
                                if (!((Set) stateCollectAsState19.getValue()).contains(((ez0) obj7).getLibrary().getId())) {
                                    arrayList2.add(obj7);
                                }
                            }
                            composer2.updateRememberedValue(arrayList2);
                            obj3 = arrayList2;
                        } else {
                            obj3 = objRememberedValue45;
                            arrayList2 = new ArrayList();
                            while (r2.hasNext()) {
                                if (!((Set) stateCollectAsState19.getValue()).contains(((ez0) obj7).getLibrary().getId())) {
                                    arrayList2.add(obj7);
                                }
                            }
                            composer2.updateRememberedValue(arrayList2);
                            obj3 = arrayList2;
                        }
                        obj3 = objRememberedValue45;
                        List list8 = (List) obj3;
                        List listB2 = mr0Var2.b();
                        String str9 = (String) state3.getValue();
                        Integer numA2 = A(stateCollectAsState9);
                        String strB3 = B(stateCollectAsState10);
                        String strC6 = C(stateCollectAsState11);
                        boolean zD2 = D(stateCollectAsState12);
                        boolean zF2 = F(stateCollectAsState14);
                        if (function5 == null) {
                            composer2.startReplaceGroup(-1689383606);
                            composer2.endReplaceGroup();
                            function10 = null;
                        } else {
                            composer2.startReplaceGroup(-1689383605);
                            zChangedInstance6 = composer2.changedInstance(fs0Var3) | composer2.changed(function5);
                            objRememberedValue46 = composer2.rememberedValue();
                            if (zChangedInstance6) {
                                objRememberedValue46 = new wh(fs0Var3, function5, mutableState30, 5);
                                composer2.updateRememberedValue(objRememberedValue46);
                            } else {
                                objRememberedValue46 = new wh(fs0Var3, function5, mutableState30, 5);
                                composer2.updateRememberedValue(objRememberedValue46);
                            }
                            composer2.endReplaceGroup();
                            function10 = (Function3) objRememberedValue46;
                        }
                        boolean zBooleanValue22 = ((Boolean) mutableState99.getValue()).booleanValue();
                        boolean zBooleanValue23 = ((Boolean) mutableState100.getValue()).booleanValue();
                        ItemCounts itemCounts3 = (ItemCounts) state5.getValue();
                        int iIntValue4 = ((Number) mutableState93.getValue()).intValue();
                        boolean zChangedInstance312 = composer2.changedInstance(fs0Var3);
                        i15 = i14 & 14;
                        if (i15 == 4) {
                            z15 = true;
                        } else {
                            z15 = false;
                        }
                        z16 = zChangedInstance312 | z15;
                        objRememberedValue47 = composer2.rememberedValue();
                        if (z16) {
                            final int i318 = 0;
                            function11 = function1;
                            objRememberedValue47 = new Function1() { // from class: cq0
                                public final Object invoke(Object obj8) {
                                    int i319 = i318;
                                    MutableState mutableState1110 = mutableState30;
                                    Function1 function115 = function11;
                                    fs0 fs0Var11 = fs0Var3;
                                    String str10 = (String) obj8;
                                    switch (i319) {
                                        case 0:
                                            str10.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, "我的媒体", uo0.b, 12);
                                            function115.invoke(str10);
                                            break;
                                        case 1:
                                            str10.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, null, null, 30);
                                            function115.invoke(str10);
                                            break;
                                        default:
                                            str10.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, null, null, 30);
                                            function115.invoke(str10);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue47);
                        } else {
                            final int i319 = 0;
                            function11 = function1;
                            objRememberedValue47 = new Function1() { // from class: cq0
                                public final Object invoke(Object obj8) {
                                    int i3110 = i319;
                                    MutableState mutableState1110 = mutableState30;
                                    Function1 function115 = function11;
                                    fs0 fs0Var11 = fs0Var3;
                                    String str10 = (String) obj8;
                                    switch (i3110) {
                                        case 0:
                                            str10.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, "我的媒体", uo0.b, 12);
                                            function115.invoke(str10);
                                            break;
                                        case 1:
                                            str10.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, null, null, 30);
                                            function115.invoke(str10);
                                            break;
                                        default:
                                            str10.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, null, null, 30);
                                            function115.invoke(str10);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue47);
                        }
                        Function1 function115 = (Function1) objRememberedValue47;
                        boolean zChangedInstance313 = composer2.changedInstance(fs0Var3);
                        if (i15 == 4) {
                            z17 = true;
                        } else {
                            z17 = false;
                        }
                        z18 = zChangedInstance313 | z17;
                        objRememberedValue48 = composer2.rememberedValue();
                        if (z18) {
                            i16 = 0;
                            objRememberedValue48 = new Function2() { // from class: dq0
                                public final Object invoke(Object obj8, Object obj9) {
                                    int i320 = i16;
                                    MutableState mutableState1110 = mutableState30;
                                    Function1 function116 = function11;
                                    fs0 fs0Var11 = fs0Var3;
                                    String str10 = (String) obj8;
                                    String str11 = (String) obj9;
                                    switch (i320) {
                                        case 0:
                                            str10.getClass();
                                            str11.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, "header_".concat(str10), str11, uo0.c, 12);
                                            function116.invoke(str10);
                                            break;
                                        default:
                                            str10.getClass();
                                            str11.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, str11, uo0.c, 12);
                                            function116.invoke(str10);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue48);
                        } else {
                            i16 = 0;
                            objRememberedValue48 = new Function2() { // from class: dq0
                                public final Object invoke(Object obj8, Object obj9) {
                                    int i320 = i16;
                                    MutableState mutableState1110 = mutableState30;
                                    Function1 function116 = function11;
                                    fs0 fs0Var11 = fs0Var3;
                                    String str10 = (String) obj8;
                                    String str11 = (String) obj9;
                                    switch (i320) {
                                        case 0:
                                            str10.getClass();
                                            str11.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, "header_".concat(str10), str11, uo0.c, 12);
                                            function116.invoke(str10);
                                            break;
                                        default:
                                            str10.getClass();
                                            str11.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, str11, uo0.c, 12);
                                            function116.invoke(str10);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue48);
                        }
                        Function2 function116 = (Function2) objRememberedValue48;
                        boolean zChangedInstance314 = composer2.changedInstance(fs0Var3);
                        if (i15 == 4) {
                            i17 = 1;
                        } else {
                            i17 = i16;
                        }
                        i18 = i17 | (zChangedInstance314 ? 1 : 0);
                        objRememberedValue49 = composer2.rememberedValue();
                        if (i18 == 0) {
                            objRememberedValue49 = new wh(fs0Var3, function11, mutableState30, 6);
                            composer2.updateRememberedValue(objRememberedValue49);
                        } else {
                            objRememberedValue49 = new wh(fs0Var3, function11, mutableState30, 6);
                            composer2.updateRememberedValue(objRememberedValue49);
                        }
                        Function3 function210 = (Function3) objRememberedValue49;
                        boolean zChangedInstance315 = composer2.changedInstance(fs0Var3);
                        i19 = i14 & 112;
                        if (i19 == 32) {
                            i20 = 1;
                        } else {
                            i20 = i16;
                        }
                        i21 = (zChangedInstance315 ? 1 : 0) | i20;
                        objRememberedValue50 = composer2.rememberedValue();
                        if (i21 == 0) {
                            final int i320 = 1;
                            objRememberedValue50 = new Function2() { // from class: dq0
                                public final Object invoke(Object obj8, Object obj9) {
                                    int i321 = i320;
                                    MutableState mutableState1110 = mutableState30;
                                    Function1 function117 = function2;
                                    fs0 fs0Var11 = fs0Var3;
                                    String str10 = (String) obj8;
                                    String str11 = (String) obj9;
                                    switch (i321) {
                                        case 0:
                                            str10.getClass();
                                            str11.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, "header_".concat(str10), str11, uo0.c, 12);
                                            function117.invoke(str10);
                                            break;
                                        default:
                                            str10.getClass();
                                            str11.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, str11, uo0.c, 12);
                                            function117.invoke(str10);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue50);
                        } else {
                            final int i321 = 1;
                            objRememberedValue50 = new Function2() { // from class: dq0
                                public final Object invoke(Object obj8, Object obj9) {
                                    int i322 = i321;
                                    MutableState mutableState1110 = mutableState30;
                                    Function1 function117 = function2;
                                    fs0 fs0Var11 = fs0Var3;
                                    String str10 = (String) obj8;
                                    String str11 = (String) obj9;
                                    switch (i322) {
                                        case 0:
                                            str10.getClass();
                                            str11.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, "header_".concat(str10), str11, uo0.c, 12);
                                            function117.invoke(str10);
                                            break;
                                        default:
                                            str10.getClass();
                                            str11.getClass();
                                            Objects.toString((to0) mutableState1110.getValue());
                                            fs0Var11.R((to0) mutableState1110.getValue());
                                            fs0.Q(fs0Var11, str10, str11, uo0.c, 12);
                                            function117.invoke(str10);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue50);
                        }
                        Function2 function211 = (Function2) objRememberedValue50;
                        boolean zChangedInstance316 = composer2.changedInstance(fs0Var3);
                        if ((i14 & 896) == 256) {
                            z19 = true;
                        } else {
                            z19 = false;
                        }
                        boolean z411 = z19 | zChangedInstance316;
                        if (i19 == 32) {
                            z20 = true;
                        } else {
                            z20 = false;
                        }
                        z21 = z411 | z20;
                        objRememberedValue51 = composer2.rememberedValue();
                        if (z21) {
                            fs0 fs0Var11 = fs0Var3;
                            mutableState31 = mutableState30;
                            z22 = false;
                            fd fdVar5 = new fd(3, fs0Var11, function3, function2, mutableState31);
                            fs0Var3 = fs0Var11;
                            composer2.updateRememberedValue(fdVar5);
                            objRememberedValue51 = fdVar5;
                        } else {
                            fs0 fs0Var12 = fs0Var3;
                            mutableState31 = mutableState30;
                            z22 = false;
                            fd fdVar6 = new fd(3, fs0Var12, function3, function2, mutableState31);
                            fs0Var3 = fs0Var12;
                            composer2.updateRememberedValue(fdVar6);
                            objRememberedValue51 = fdVar6;
                        }
                        Function2 function212 = (Function2) objRememberedValue51;
                        zChangedInstance7 = composer2.changedInstance(fs0Var3);
                        objRememberedValue52 = composer2.rememberedValue();
                        if (zChangedInstance7) {
                            final int i322 = 1;
                            objRememberedValue52 = new Function0() { // from class: bq0
                                public final Object invoke() throws NoSuchAlgorithmException {
                                    int i3110 = i322;
                                    fs0 fs0Var13 = fs0Var3;
                                    switch (i3110) {
                                        case 0:
                                            fs0Var13.G(false, false);
                                            break;
                                        case 1:
                                            fs0Var13.k.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            fs0Var13.G(false, false);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue52);
                        } else {
                            final int i323 = 1;
                            objRememberedValue52 = new Function0() { // from class: bq0
                                public final Object invoke() throws NoSuchAlgorithmException {
                                    int i3110 = i323;
                                    fs0 fs0Var13 = fs0Var3;
                                    switch (i3110) {
                                        case 0:
                                            fs0Var13.G(false, false);
                                            break;
                                        case 1:
                                            fs0Var13.k.setValue(Boolean.FALSE);
                                            break;
                                        default:
                                            fs0Var13.G(false, false);
                                            break;
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            composer2.updateRememberedValue(objRememberedValue52);
                        }
                        Function0 function213 = (Function0) objRememberedValue52;
                        objRememberedValue53 = composer2.rememberedValue();
                        companion4 = Composer.Companion;
                        if (objRememberedValue53 == companion4.getEmpty()) {
                            state6 = r79;
                            objRememberedValue53 = new z70(23, state6);
                            composer2.updateRememberedValue(objRememberedValue53);
                        } else {
                            state6 = r79;
                        }
                        Function1 function214 = (Function1) objRememberedValue53;
                        objRememberedValue54 = composer2.rememberedValue();
                        if (objRememberedValue54 == companion4.getEmpty()) {
                            objRememberedValue54 = new z1(state6, mutableState90, mutableState91, 4);
                            composer2.updateRememberedValue(objRememberedValue54);
                        }
                        Function1 function215 = (Function1) objRememberedValue54;
                        objRememberedValue55 = composer2.rememberedValue();
                        if (objRememberedValue55 == companion4.getEmpty()) {
                            objRememberedValue55 = new x50(22, mutableState93);
                            composer2.updateRememberedValue(objRememberedValue55);
                        }
                        state8 = state6;
                        a(z, list7, listB2, list8, str9, numA2, strB3, strC6, zD2, zF2, function115, function116, function210, function211, function212, function10, function213, fs0Var3, zBooleanValue22, zBooleanValue23, itemCounts3, function214, function215, iIntValue4, (Function0) objRememberedValue55, composer2, i12, (i31 << 18) & 29360128, 25008);
                        composer2 = composer2;
                        composer2.endReplaceGroup();
                    }
                }
                composer2.endReplaceGroup();
                Unit unit8 = Unit.INSTANCE;
            }
            composer2.endNode();
            strC2 = M(mutableState22).c();
            serverPreferences5 = serverPreferences4;
            if (strC2 != null) {
                host = str;
            } else {
                host = str;
            }
            if (N(stateCollectAsState18).getEnabled()) {
                z23 = z22;
            } else {
                z23 = z22;
            }
            if (N(stateCollectAsState18).getEnabled()) {
                z24 = z22;
            } else {
                z24 = z22;
            }
            if (z) {
                composer2.startReplaceGroup(1337618891);
                to0 to0VarH8 = H(mutableState31);
                objRememberedValue121 = composer2.rememberedValue();
                companion9 = Composer.Companion;
                if (objRememberedValue121 == companion9.getEmpty()) {
                    objRememberedValue121 = new z70(26, mutableState31);
                    composer2.updateRememberedValue(objRememberedValue121);
                }
                Function1 function216 = (Function1) objRememberedValue121;
                boolean z53 = !((Boolean) mutableState97.getValue()).booleanValue();
                boolean zBooleanValue24 = ((Boolean) mutableState98.getValue()).booleanValue();
                String strA4 = M(mutableState22).a();
                String strB4 = M(mutableState22).b();
                objRememberedValue122 = composer2.rememberedValue();
                if (objRememberedValue122 == companion9.getEmpty()) {
                    mutableState33 = mutableState28;
                    objRememberedValue122 = new x50(23, mutableState33);
                    composer2.updateRememberedValue(objRememberedValue122);
                } else {
                    mutableState33 = mutableState28;
                }
                Function0 function217 = (Function0) objRememberedValue122;
                objRememberedValue123 = composer2.rememberedValue();
                if (objRememberedValue123 == companion9.getEmpty()) {
                    mutableState = mutableState;
                    mutableState36 = mutableState2;
                    objRememberedValue123 = new yo0(12, mutableState36, mutableState);
                    composer2.updateRememberedValue(objRememberedValue123);
                } else {
                    mutableState = mutableState;
                    mutableState36 = mutableState2;
                }
                Function0 function218 = (Function0) objRememberedValue123;
                objRememberedValue124 = composer2.rememberedValue();
                if (objRememberedValue124 == companion9.getEmpty()) {
                    mutableState34 = mutableState108;
                    mutableState35 = mutableState109;
                    objRememberedValue124 = new ep0(mutableState31, mutableState35, mutableState34, 1);
                    composer2.updateRememberedValue(objRememberedValue124);
                } else {
                    mutableState34 = mutableState108;
                    mutableState35 = mutableState109;
                }
                Function0 function219 = (Function0) objRememberedValue124;
                objRememberedValue125 = composer2.rememberedValue();
                if (objRememberedValue125 == companion9.getEmpty()) {
                    objRememberedValue125 = new ep0(mutableState31, mutableState35, mutableState34, 2);
                    composer2.updateRememberedValue(objRememberedValue125);
                }
                Function0 function310 = (Function0) objRememberedValue125;
                zChangedInstance27 = composer2.changedInstance(fs0Var3);
                objRememberedValue126 = composer2.rememberedValue();
                if (zChangedInstance27) {
                    final int i324 = 2;
                    objRememberedValue126 = new Function0() { // from class: bq0
                        public final Object invoke() throws NoSuchAlgorithmException {
                            int i3110 = i324;
                            fs0 fs0Var13 = fs0Var3;
                            switch (i3110) {
                                case 0:
                                    fs0Var13.G(false, false);
                                    break;
                                case 1:
                                    fs0Var13.k.setValue(Boolean.FALSE);
                                    break;
                                default:
                                    fs0Var13.G(false, false);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composer2.updateRememberedValue(objRememberedValue126);
                } else {
                    final int i325 = 2;
                    objRememberedValue126 = new Function0() { // from class: bq0
                        public final Object invoke() throws NoSuchAlgorithmException {
                            int i3110 = i325;
                            fs0 fs0Var13 = fs0Var3;
                            switch (i3110) {
                                case 0:
                                    fs0Var13.G(false, false);
                                    break;
                                case 1:
                                    fs0Var13.k.setValue(Boolean.FALSE);
                                    break;
                                default:
                                    fs0Var13.G(false, false);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composer2.updateRememberedValue(objRememberedValue126);
                }
                Function0 function311 = (Function0) objRememberedValue126;
                boolean zBooleanValue25 = ((Boolean) state8.getValue()).booleanValue();
                if (N(stateCollectAsState18).getEnabled()) {
                    z37 = z22;
                } else {
                    z37 = z22;
                }
                if (((Boolean) state4.getValue()).booleanValue()) {
                    bdVar2.getClass();
                    if (((Boolean) xc.b.getValue()).booleanValue()) {
                        z38 = true;
                    } else {
                        z38 = z22;
                    }
                } else {
                    z38 = z22;
                }
                int iIntValue5 = ((Number) mutableState90.getValue()).intValue();
                int iIntValue6 = ((Number) mutableState91.getValue()).intValue();
                int iZ2 = z(mutableState92);
                objRememberedValue127 = composer2.rememberedValue();
                if (objRememberedValue127 == companion9.getEmpty()) {
                    objRememberedValue127 = new x50(24, mutableState90);
                    composer2.updateRememberedValue(objRememberedValue127);
                }
                Function0 function312 = (Function0) objRememberedValue127;
                objRememberedValue128 = composer2.rememberedValue();
                if (objRememberedValue128 == companion9.getEmpty()) {
                    objRememberedValue128 = new x50(25, mutableState91);
                    composer2.updateRememberedValue(objRememberedValue128);
                }
                Function0 function313 = (Function0) objRememberedValue128;
                objRememberedValue129 = composer2.rememberedValue();
                if (objRememberedValue129 == companion9.getEmpty()) {
                    objRememberedValue129 = new z70(27, mutableState25);
                    composer2.updateRememberedValue(objRememberedValue129);
                }
                Composer composer211 = composer2;
                ya2.d(to0VarH8, function216, z53, zBooleanValue24, strA4, strB4, function217, function218, function219, function310, function311, zBooleanValue25, z24, z37, z23, z38, focusRequester, iIntValue5, iIntValue6, iZ2, function312, function313, (Function1) objRememberedValue129, boxScopeInstance.align(Modifier.Companion, Alignment.Companion.getTopCenter()), composer211, 920125488, 1572864, 438);
                composer2 = composer211;
                composer2.endReplaceGroup();
            } else {
                i14 = i14;
                state5 = state5;
                mutableState33 = mutableState28;
                mutableState34 = mutableState108;
                mutableState35 = mutableState109;
                mutableState36 = mutableState2;
                composer2.startReplaceGroup(1339902227);
                composer2.endReplaceGroup();
            }
            composer2.endNode();
            if (z) {
                composer2.startReplaceGroup(1418126017);
                if (((Boolean) mutableState89.getValue()).booleanValue()) {
                    composer2.startReplaceGroup(1417513147);
                    objRememberedValue118 = composer2.rememberedValue();
                    companion8 = Composer.Companion;
                    if (objRememberedValue118 == companion8.getEmpty()) {
                        mutableState85 = mutableState89;
                        objRememberedValue118 = new x50(26, mutableState85);
                        composer2.updateRememberedValue(objRememberedValue118);
                    } else {
                        mutableState85 = mutableState89;
                    }
                    Function0 function314 = (Function0) objRememberedValue118;
                    context4 = context;
                    zChangedInstance26 = composer2.changedInstance(context4);
                    objRememberedValue119 = composer2.rememberedValue();
                    if (zChangedInstance26) {
                        objRememberedValue119 = new m4(context4, 1);
                        composer2.updateRememberedValue(objRememberedValue119);
                    } else {
                        objRememberedValue119 = new m4(context4, 1);
                        composer2.updateRememberedValue(objRememberedValue119);
                    }
                    Function0 function315 = (Function0) objRememberedValue119;
                    if ((i14 & 3670016) == 1048576) {
                        z36 = true;
                    } else {
                        z36 = z22;
                    }
                    objRememberedValue120 = composer2.rememberedValue();
                    if (z36) {
                        objRememberedValue120 = new u5(function0, mutableState85, 2);
                        composer2.updateRememberedValue(objRememberedValue120);
                    } else {
                        objRememberedValue120 = new u5(function0, mutableState85, 2);
                        composer2.updateRememberedValue(objRememberedValue120);
                    }
                    Composer composer212 = composer2;
                    context2 = context4;
                    ri0.a(function314, function315, (Function0) objRememberedValue120, "是否退出", "退出软件", "退回服务页", Dp.constructor-impl(400.0f), Dp.constructor-impl(198.0f), Dp.constructor-impl(156.0f), composer212, 115043334, 512);
                    composer2 = composer212;
                    composer2.endReplaceGroup();
                } else {
                    context2 = context;
                    composer2.startReplaceGroup(1418095513);
                    composer2.endReplaceGroup();
                }
                if (((Boolean) mutableState112.getValue()).booleanValue()) {
                    composer2.startReplaceGroup(1418157482);
                    nr0Var2 = (nr0) stateCollectAsState7.getValue();
                    if (nr0Var2 instanceof mr0) {
                        composer2.startReplaceGroup(1418234052);
                        List list9 = ((mr0) nr0Var2).a;
                        Set set2 = (Set) stateCollectAsState19.getValue();
                        coroutineScope = coroutineScope3;
                        mutableState37 = mutableState22;
                        zChangedInstance25 = composer2.changedInstance(coroutineScope) | composer2.changedInstance(z01Var2) | composer2.changed(mutableState37);
                        objRememberedValue116 = composer2.rememberedValue();
                        if (zChangedInstance25) {
                            objRememberedValue116 = new t4(coroutineScope, z01Var2, mutableState37, 6);
                            composer2.updateRememberedValue(objRememberedValue116);
                        } else {
                            objRememberedValue116 = new t4(coroutineScope, z01Var2, mutableState37, 6);
                            composer2.updateRememberedValue(objRememberedValue116);
                        }
                        Function2 function316 = (Function2) objRememberedValue116;
                        objRememberedValue117 = composer2.rememberedValue();
                        if (objRememberedValue117 == Composer.Companion.getEmpty()) {
                            mutableState38 = mutableState112;
                            objRememberedValue117 = new x50(27, mutableState38);
                            composer2.updateRememberedValue(objRememberedValue117);
                        } else {
                            mutableState38 = mutableState112;
                        }
                        Composer composer213 = composer2;
                        t01.a(list9, set2, function316, (Function0) objRememberedValue117, composer213, 3072);
                        composer2 = composer213;
                        composer2.endReplaceGroup();
                    } else {
                        mutableState37 = mutableState22;
                        mutableState38 = mutableState112;
                        coroutineScope = coroutineScope3;
                        composer2.startReplaceGroup(1418746265);
                        composer2.endReplaceGroup();
                    }
                    composer2.endReplaceGroup();
                } else {
                    mutableState37 = mutableState22;
                    mutableState38 = mutableState112;
                    coroutineScope = coroutineScope3;
                    composer2.startReplaceGroup(1418756185);
                    composer2.endReplaceGroup();
                }
                if (((Boolean) mutableState113.getValue()).booleanValue()) {
                    composer2.startReplaceGroup(1418807149);
                    List listM2 = m(mutableState115);
                    zChangedInstance23 = composer2.changedInstance(fs0Var3);
                    objRememberedValue113 = composer2.rememberedValue();
                    if (zChangedInstance23) {
                        objRememberedValue113 = new r90(fs0Var3);
                        composer2.updateRememberedValue(objRememberedValue113);
                    } else {
                        objRememberedValue113 = new r90(fs0Var3);
                        composer2.updateRememberedValue(objRememberedValue113);
                    }
                    Function2 function317 = (KFunction) objRememberedValue113;
                    zChangedInstance24 = composer2.changedInstance(fs0Var3);
                    objRememberedValue114 = composer2.rememberedValue();
                    if (zChangedInstance24) {
                        objRememberedValue114 = new cp0(fs0Var3, 2);
                        composer2.updateRememberedValue(objRememberedValue114);
                    } else {
                        objRememberedValue114 = new cp0(fs0Var3, 2);
                        composer2.updateRememberedValue(objRememberedValue114);
                    }
                    Function1 function318 = (Function1) objRememberedValue114;
                    objRememberedValue115 = composer2.rememberedValue();
                    if (objRememberedValue115 == Composer.Companion.getEmpty()) {
                        mutableState39 = mutableState115;
                        mutableState40 = mutableState113;
                        objRememberedValue115 = new yo0(13, mutableState40, mutableState39);
                        composer2.updateRememberedValue(objRememberedValue115);
                    } else {
                        mutableState39 = mutableState115;
                        mutableState40 = mutableState113;
                    }
                    Composer composer214 = composer2;
                    g01.b(listM2, function317, function318, (Function0) objRememberedValue115, composer214, 24576);
                    composer2 = composer214;
                    composer2.endReplaceGroup();
                } else {
                    mutableState33 = mutableState33;
                    mutableState39 = mutableState115;
                    mutableState40 = mutableState113;
                    composer2.startReplaceGroup(1419249209);
                    composer2.endReplaceGroup();
                }
                if (((Boolean) mutableState114.getValue()).booleanValue()) {
                    composer2.startReplaceGroup(1419321873);
                    objRememberedValue112 = composer2.rememberedValue();
                    if (objRememberedValue112 == Composer.Companion.getEmpty()) {
                        mutableState41 = mutableState114;
                        mutableState84 = mutableState116;
                        objRememberedValue112 = new y5(mutableState84, mutableState41, mutableState39, 15);
                        composer2.updateRememberedValue(objRememberedValue112);
                    } else {
                        mutableState41 = mutableState114;
                        mutableState84 = mutableState116;
                    }
                    mutableState42 = mutableState84;
                    i23 = 15;
                    Composer composer215 = composer2;
                    AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue112, st.i(), (Modifier) null, ComposableLambdaKt.rememberComposableLambda(646043598, true, new t4(mutableState84, mutableState41, mutableState39, 5), composer2, 54), (Function2) null, st.d(), st.a(), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer215, 1772598, 0, 16276);
                    composer4 = composer215;
                    composer4.endReplaceGroup();
                } else {
                    mutableState40 = mutableState40;
                    composer4 = composer2;
                    i23 = 15;
                    mutableState41 = mutableState114;
                    mutableState42 = mutableState116;
                    composer4.startReplaceGroup(1420221369);
                    composer4.endReplaceGroup();
                }
                if (((Boolean) mutableState34.getValue()).booleanValue()) {
                    composer4.startReplaceGroup(1420330768);
                    ItemCounts itemCounts4 = (ItemCounts) state5.getValue();
                    boolean zJ3 = j(mutableState35);
                    objRememberedValue108 = composer4.rememberedValue();
                    companion7 = Composer.Companion;
                    if (objRememberedValue108 == companion7.getEmpty()) {
                        mutableState110 = mutableState110;
                        objRememberedValue108 = new y5(mutableState34, mutableState35, mutableState110, 16);
                        composer4.updateRememberedValue(objRememberedValue108);
                    } else {
                        mutableState110 = mutableState110;
                    }
                    Function0 function319 = (Function0) objRememberedValue108;
                    objRememberedValue109 = composer4.rememberedValue();
                    if (objRememberedValue109 == companion7.getEmpty()) {
                        objRememberedValue109 = new y5(mutableState34, mutableState35, mutableState38, 17);
                        composer4.updateRememberedValue(objRememberedValue109);
                    }
                    Function0 function410 = (Function0) objRememberedValue109;
                    zChangedInstance22 = composer4.changedInstance(coroutineScope) | composer4.changedInstance(fs0Var3) | composer4.changedInstance(context2);
                    objRememberedValue110 = composer4.rememberedValue();
                    if (zChangedInstance22) {
                        objRememberedValue110 = new d4(coroutineScope, mutableState34, mutableState35, mutableState42, fs0Var3, mutableState41, mutableState39, mutableState40, context2, 3);
                        composer4.updateRememberedValue(objRememberedValue110);
                    } else {
                        objRememberedValue110 = new d4(coroutineScope, mutableState34, mutableState35, mutableState42, fs0Var3, mutableState41, mutableState39, mutableState40, context2, 3);
                        composer4.updateRememberedValue(objRememberedValue110);
                    }
                    Function0 function411 = (Function0) objRememberedValue110;
                    objRememberedValue111 = composer4.rememberedValue();
                    if (objRememberedValue111 == companion7.getEmpty()) {
                        objRememberedValue111 = new f4(23, mutableState34, mutableState35);
                        composer4.updateRememberedValue(objRememberedValue111);
                    }
                    so0.a(itemCounts4, zJ3, function319, function410, function411, (Function0) objRememberedValue111, composer4, 200064);
                    composer4.endReplaceGroup();
                } else {
                    composer4.startReplaceGroup(1422645817);
                    composer4.endReplaceGroup();
                }
                if (((Boolean) mutableState110.getValue()).booleanValue()) {
                    composer4.startReplaceGroup(1422691728);
                    zChanged10 = composer4.changed(mutableState37) | composer4.changedInstance(serverPreferences5) | composer4.changedInstance(context2) | composer4.changedInstance(fs0Var3);
                    objRememberedValue106 = composer4.rememberedValue();
                    if (zChanged10) {
                        MutableState mutableState1110 = mutableState37;
                        MutableState mutableState1111 = mutableState110;
                        k1 k1Var2 = new k1(serverPreferences5, context2, fs0Var3, mutableState1110, mutableState1111, 7);
                        mutableState43 = mutableState1110;
                        mutableState83 = mutableState1111;
                        composer4.updateRememberedValue(k1Var2);
                        objRememberedValue106 = k1Var2;
                    } else {
                        MutableState mutableState1112 = mutableState37;
                        MutableState mutableState1113 = mutableState110;
                        k1 k1Var3 = new k1(serverPreferences5, context2, fs0Var3, mutableState1112, mutableState1113, 7);
                        mutableState43 = mutableState1112;
                        mutableState83 = mutableState1113;
                        composer4.updateRememberedValue(k1Var3);
                        objRememberedValue106 = k1Var3;
                    }
                    Function1 function412 = (Function1) objRememberedValue106;
                    objRememberedValue107 = composer4.rememberedValue();
                    if (objRememberedValue107 == Composer.Companion.getEmpty()) {
                        objRememberedValue107 = new x50(i23, mutableState83);
                        composer4.updateRememberedValue(objRememberedValue107);
                    }
                    Composer composer216 = composer4;
                    so0.d(null, list, true, function412, (Function0) objRememberedValue107, composer216, 24960, 1);
                    composer5 = composer216;
                    composer5.endReplaceGroup();
                } else {
                    composer5 = composer4;
                    mutableState43 = mutableState37;
                    composer5.startReplaceGroup(1422977145);
                    composer5.endReplaceGroup();
                }
                if (((Boolean) mutableState33.getValue()).booleanValue()) {
                    composer5.startReplaceGroup(1423040540);
                    if ((i14 & 3670016) == 1048576) {
                        z32 = true;
                    } else {
                        z32 = z22;
                    }
                    if ((i14 & 29360128) == 8388608) {
                        z33 = true;
                    } else {
                        z33 = z22;
                    }
                    boolean zChangedInstance317 = z33 | z32 | composer5.changedInstance(serverPreferences5) | composer5.changedInstance(context2) | composer5.changed(mutableState43) | composer5.changedInstance(fs0Var3);
                    if ((i14 & 458752) == 131072) {
                        z34 = true;
                    } else {
                        z34 = z22;
                    }
                    z35 = zChangedInstance317 | z34;
                    objRememberedValue104 = composer5.rememberedValue();
                    if (z35) {
                        fs0 fs0Var13 = fs0Var3;
                        Context context7 = context2;
                        MutableState mutableState1114 = mutableState33;
                        i24 = 24;
                        i28 = 16;
                        wo0 wo0Var2 = new wo0(function0, function7, mutableState1114, serverPreferences5, context7, mutableState43, fs0Var13, function6, mutableState31, (MutableState) state8);
                        context2 = context7;
                        mutableState82 = mutableState1114;
                        fs0Var3 = fs0Var13;
                        composer5.updateRememberedValue(wo0Var2);
                        objRememberedValue104 = wo0Var2;
                    } else {
                        fs0 fs0Var14 = fs0Var3;
                        Context context8 = context2;
                        MutableState mutableState1115 = mutableState33;
                        i24 = 24;
                        i28 = 16;
                        wo0 wo0Var3 = new wo0(function0, function7, mutableState1115, serverPreferences5, context8, mutableState43, fs0Var14, function6, mutableState31, (MutableState) state8);
                        context2 = context8;
                        mutableState82 = mutableState1115;
                        fs0Var3 = fs0Var14;
                        composer5.updateRememberedValue(wo0Var3);
                        objRememberedValue104 = wo0Var3;
                    }
                    Function1 function413 = (Function1) objRememberedValue104;
                    objRememberedValue105 = composer5.rememberedValue();
                    if (objRememberedValue105 == Composer.Companion.getEmpty()) {
                        objRememberedValue105 = new x50(i28, mutableState82);
                        composer5.updateRememberedValue(objRememberedValue105);
                    }
                    Composer composer217 = composer5;
                    so0.d("切换服务器", listA, true, function413, (Function0) objRememberedValue105, composer217, 24966, 0);
                    composer6 = composer217;
                    composer6.endReplaceGroup();
                } else {
                    coroutineScope = coroutineScope;
                    serverPreferences5 = serverPreferences5;
                    mutableState43 = mutableState43;
                    composer6 = composer5;
                    bdVar2 = bdVar2;
                    vc2Var = vc2Var;
                    mutableState = mutableState;
                    i24 = 24;
                    composer6.startReplaceGroup(1423837209);
                    composer6.endReplaceGroup();
                }
                if (((Boolean) mutableState.getValue()).booleanValue()) {
                    composer6.startReplaceGroup(1423961395);
                    objRememberedValue89 = composer6.rememberedValue();
                    companion6 = Composer.Companion;
                    if (objRememberedValue89 == companion6.getEmpty()) {
                        mutableState45 = mutableState;
                        objRememberedValue89 = new f4(i24, mutableState45, mutableState36);
                        composer6.updateRememberedValue(objRememberedValue89);
                    } else {
                        mutableState45 = mutableState;
                    }
                    Function0 function414 = (Function0) objRememberedValue89;
                    objRememberedValue90 = composer6.rememberedValue();
                    if (objRememberedValue90 == companion6.getEmpty()) {
                        mutableState3 = mutableState3;
                        objRememberedValue90 = new y5(mutableState3, mutableState36, mutableState45, 18);
                        composer6.updateRememberedValue(objRememberedValue90);
                    } else {
                        mutableState3 = mutableState3;
                    }
                    Function0 function415 = (Function0) objRememberedValue90;
                    objRememberedValue91 = composer6.rememberedValue();
                    if (objRememberedValue91 == companion6.getEmpty()) {
                        mutableState47 = mutableState5;
                        objRememberedValue91 = new y5(mutableState47, mutableState36, mutableState45, 19);
                        composer6.updateRememberedValue(objRememberedValue91);
                    } else {
                        mutableState47 = mutableState5;
                    }
                    Function0 function416 = (Function0) objRememberedValue91;
                    objRememberedValue92 = composer6.rememberedValue();
                    if (objRememberedValue92 == companion6.getEmpty()) {
                        mutableState79 = mutableState6;
                        objRememberedValue92 = new y5(mutableState79, mutableState36, mutableState45, 20);
                        composer6.updateRememberedValue(objRememberedValue92);
                    } else {
                        mutableState79 = mutableState6;
                    }
                    Function0 function417 = (Function0) objRememberedValue92;
                    objRememberedValue93 = composer6.rememberedValue();
                    if (objRememberedValue93 == companion6.getEmpty()) {
                        mutableState49 = mutableState7;
                        objRememberedValue93 = new y5(mutableState49, mutableState36, mutableState45, 21);
                        composer6.updateRememberedValue(objRememberedValue93);
                    } else {
                        mutableState49 = mutableState7;
                    }
                    Function0 function418 = (Function0) objRememberedValue93;
                    objRememberedValue94 = composer6.rememberedValue();
                    if (objRememberedValue94 == companion6.getEmpty()) {
                        mutableState80 = mutableState4;
                        objRememberedValue94 = new y5(mutableState80, mutableState36, mutableState45, 22);
                        composer6.updateRememberedValue(objRememberedValue94);
                    } else {
                        mutableState80 = mutableState4;
                    }
                    Function0 function419 = (Function0) objRememberedValue94;
                    objRememberedValue95 = composer6.rememberedValue();
                    mutableState46 = mutableState80;
                    if (objRememberedValue95 == companion6.getEmpty()) {
                        mutableState50 = mutableState8;
                        objRememberedValue95 = new y5(mutableState50, mutableState36, mutableState45, 23);
                        composer6.updateRememberedValue(objRememberedValue95);
                    } else {
                        mutableState50 = mutableState8;
                    }
                    Function0 function510 = (Function0) objRememberedValue95;
                    objRememberedValue96 = composer6.rememberedValue();
                    if (objRememberedValue96 == companion6.getEmpty()) {
                        mutableState51 = mutableState9;
                        objRememberedValue96 = new y5(mutableState51, mutableState36, mutableState45, 24);
                        composer6.updateRememberedValue(objRememberedValue96);
                    } else {
                        mutableState51 = mutableState9;
                    }
                    Function0 function511 = (Function0) objRememberedValue96;
                    zChanged9 = composer6.changed(bdVar2) | composer6.changedInstance(context2);
                    objRememberedValue97 = composer6.rememberedValue();
                    if (zChanged9) {
                        MutableState mutableState124 = mutableState36;
                        bd bdVar6 = bdVar2;
                        mutableState48 = mutableState79;
                        z5 z5Var2 = new z5(bdVar6, context2, mutableState124, mutableState45, 1);
                        bdVar4 = bdVar6;
                        mutableState44 = mutableState124;
                        composer6.updateRememberedValue(z5Var2);
                        objRememberedValue97 = z5Var2;
                    } else {
                        MutableState mutableState125 = mutableState36;
                        bd bdVar7 = bdVar2;
                        mutableState48 = mutableState79;
                        z5 z5Var3 = new z5(bdVar7, context2, mutableState125, mutableState45, 1);
                        bdVar4 = bdVar7;
                        mutableState44 = mutableState125;
                        composer6.updateRememberedValue(z5Var3);
                        objRememberedValue97 = z5Var3;
                    }
                    Function0 function512 = (Function0) objRememberedValue97;
                    objRememberedValue98 = composer6.rememberedValue();
                    if (objRememberedValue98 == companion6.getEmpty()) {
                        mutableState81 = mutableState10;
                        objRememberedValue98 = new y5(mutableState81, mutableState44, mutableState45, 25);
                        composer6.updateRememberedValue(objRememberedValue98);
                    } else {
                        mutableState81 = mutableState10;
                    }
                    Function0 function513 = (Function0) objRememberedValue98;
                    objRememberedValue99 = composer6.rememberedValue();
                    if (objRememberedValue99 == companion6.getEmpty()) {
                        mutableState53 = mutableState11;
                        objRememberedValue99 = new y5(mutableState53, mutableState44, mutableState45, 26);
                        composer6.updateRememberedValue(objRememberedValue99);
                    } else {
                        mutableState53 = mutableState11;
                    }
                    Function0 function514 = (Function0) objRememberedValue99;
                    objRememberedValue100 = composer6.rememberedValue();
                    if (objRememberedValue100 == companion6.getEmpty()) {
                        mutableState54 = mutableState13;
                        objRememberedValue100 = new y5(mutableState54, mutableState44, mutableState45, 27);
                        composer6.updateRememberedValue(objRememberedValue100);
                    } else {
                        mutableState54 = mutableState13;
                    }
                    Function0 function515 = (Function0) objRememberedValue100;
                    objRememberedValue101 = composer6.rememberedValue();
                    if (objRememberedValue101 == companion6.getEmpty()) {
                        mutableState14 = mutableState14;
                        objRememberedValue101 = new y5(mutableState14, mutableState44, mutableState45, 28);
                        composer6.updateRememberedValue(objRememberedValue101);
                    } else {
                        mutableState14 = mutableState14;
                    }
                    Function0 function516 = (Function0) objRememberedValue101;
                    objRememberedValue102 = composer6.rememberedValue();
                    if (objRememberedValue102 == companion6.getEmpty()) {
                        mutableState55 = mutableState15;
                        objRememberedValue102 = new y5(mutableState55, mutableState44, mutableState45, 29);
                        composer6.updateRememberedValue(objRememberedValue102);
                    } else {
                        mutableState55 = mutableState15;
                    }
                    Function0 function517 = (Function0) objRememberedValue102;
                    zChangedInstance21 = composer6.changedInstance(fs0Var3) | composer6.changedInstance(context2);
                    composer13 = composer6;
                    objRememberedValue103 = composer13.rememberedValue();
                    if (zChangedInstance21) {
                        context3 = context2;
                        composer14 = composer13;
                        MutableState mutableState126 = mutableState44;
                        fs0 fs0Var15 = fs0Var3;
                        objRememberedValue103 = new x1(fs0Var15, context3, mutableState16, mutableState17, mutableState126, mutableState45);
                        fs0Var3 = fs0Var15;
                        mutableState44 = mutableState126;
                        composer14.updateRememberedValue(objRememberedValue103);
                    } else {
                        context3 = context2;
                        composer14 = composer13;
                        MutableState mutableState127 = mutableState44;
                        fs0 fs0Var16 = fs0Var3;
                        objRememberedValue103 = new x1(fs0Var16, context3, mutableState16, mutableState17, mutableState127, mutableState45);
                        fs0Var3 = fs0Var16;
                        mutableState44 = mutableState127;
                        composer14.updateRememberedValue(objRememberedValue103);
                    }
                    Composer composer218 = composer14;
                    mutableState52 = mutableState81;
                    bdVar3 = bdVar4;
                    f12.b(function414, function415, function416, function417, function418, function419, function510, function511, false, function512, function513, function514, function515, function516, function517, (Function0) objRememberedValue103, o(mutableState44), composer218, 14380470, 28086);
                    composer7 = composer218;
                    composer7.endReplaceGroup();
                } else {
                    mutableState44 = mutableState36;
                    bdVar3 = bdVar2;
                    composer7 = composer6;
                    context3 = context2;
                    serverPreferences5 = serverPreferences5;
                    mutableState45 = mutableState;
                    mutableState46 = mutableState4;
                    mutableState47 = mutableState5;
                    mutableState48 = mutableState6;
                    mutableState49 = mutableState7;
                    mutableState50 = mutableState8;
                    mutableState51 = mutableState9;
                    mutableState52 = mutableState10;
                    mutableState53 = mutableState11;
                    mutableState54 = mutableState13;
                    mutableState55 = mutableState15;
                    composer7.startReplaceGroup(1426827097);
                    composer7.endReplaceGroup();
                }
                objRememberedValue59 = composer7.rememberedValue();
                companion5 = Composer.Companion;
                if (objRememberedValue59 == companion5.getEmpty()) {
                    objRememberedValue59 = new f4(25, mutableState44, mutableState45);
                    composer7.updateRememberedValue(objRememberedValue59);
                }
                id.a(bdVar3, (Function0) objRememberedValue59, composer7, 48);
                if (((Boolean) mutableState3.getValue()).booleanValue()) {
                    composer7.startReplaceGroup(1427129378);
                    objRememberedValue88 = composer7.rememberedValue();
                    if (objRememberedValue88 == companion5.getEmpty()) {
                        objRememberedValue88 = new f4(26, mutableState3, mutableState45);
                        composer7.updateRememberedValue(objRememberedValue88);
                    }
                    Composer composer219 = composer7;
                    v00.a((Function0) objRememberedValue88, true, null, composer219, 54, 4);
                    composer8 = composer219;
                    composer8.endReplaceGroup();
                } else {
                    composer8 = composer7;
                    composer8.startReplaceGroup(1427305241);
                    composer8.endReplaceGroup();
                }
                if (((Boolean) mutableState46.getValue()).booleanValue()) {
                    composer8.startReplaceGroup(1427347277);
                    objRememberedValue87 = composer8.rememberedValue();
                    if (objRememberedValue87 == companion5.getEmpty()) {
                        objRememberedValue87 = new f4(27, mutableState46, mutableState45);
                        composer8.updateRememberedValue(objRememberedValue87);
                    }
                    i25 = 6;
                    ad0.b((Function0) objRememberedValue87, composer8, 6);
                    composer8.endReplaceGroup();
                } else {
                    i25 = 6;
                    composer8.startReplaceGroup(1427481817);
                    composer8.endReplaceGroup();
                }
                if (((Boolean) mutableState52.getValue()).booleanValue()) {
                    composer8.startReplaceGroup(1427541182);
                    objRememberedValue85 = composer8.rememberedValue();
                    if (objRememberedValue85 == companion5.getEmpty()) {
                        objRememberedValue85 = new f4(28, mutableState52, mutableState45);
                        composer8.updateRememberedValue(objRememberedValue85);
                    }
                    Function0 function518 = (Function0) objRememberedValue85;
                    userPreferences = userPreferences2;
                    zChangedInstance20 = composer8.changedInstance(userPreferences) | composer8.changedInstance(context3);
                    objRememberedValue86 = composer8.rememberedValue();
                    if (zChangedInstance20) {
                        state7 = state11;
                        objRememberedValue86 = new e4(userPreferences, context3, state7, 1);
                        composer8.updateRememberedValue(objRememberedValue86);
                    } else {
                        state7 = state11;
                        objRememberedValue86 = new e4(userPreferences, context3, state7, 1);
                        composer8.updateRememberedValue(objRememberedValue86);
                    }
                    c61.a(function518, (Function0) objRememberedValue86, composer8, i25);
                    composer8.endReplaceGroup();
                } else {
                    userPreferences = userPreferences2;
                    state7 = state11;
                    composer8.startReplaceGroup(1428151417);
                    composer8.endReplaceGroup();
                }
                if (((Boolean) mutableState48.getValue()).booleanValue()) {
                    composer8.startReplaceGroup(1428194600);
                    objRememberedValue84 = composer8.rememberedValue();
                    if (objRememberedValue84 == companion5.getEmpty()) {
                        objRememberedValue84 = new f4(29, mutableState48, mutableState45);
                        composer8.updateRememberedValue(objRememberedValue84);
                    }
                    r92.f((Function0) objRememberedValue84, composer8, i25);
                    composer8.endReplaceGroup();
                } else {
                    composer8.startReplaceGroup(1428333945);
                    composer8.endReplaceGroup();
                }
                if (((Boolean) mutableState47.getValue()).booleanValue()) {
                    composer8.startReplaceGroup(1428554882);
                    boolean zBooleanValue26 = ((Boolean) mutableState95.getValue()).booleanValue();
                    boolean zBooleanValue27 = ((Boolean) mutableState96.getValue()).booleanValue();
                    boolean zBooleanValue110 = ((Boolean) mutableState97.getValue()).booleanValue();
                    boolean zBooleanValue111 = ((Boolean) mutableState98.getValue()).booleanValue();
                    boolean zBooleanValue112 = ((Boolean) mutableState99.getValue()).booleanValue();
                    boolean zBooleanValue113 = ((Boolean) mutableState100.getValue()).booleanValue();
                    boolean zBooleanValue114 = ((Boolean) state.getValue()).booleanValue();
                    sharedPreferences = xu.e;
                    if (sharedPreferences == null) {
                        z30 = false;
                        z29 = false;
                    } else {
                        z29 = false;
                        z30 = sharedPreferences.getBoolean("per_library_sort_enabled", false);
                    }
                    sharedPreferences2 = xu.e;
                    if (sharedPreferences2 == null) {
                        z31 = z29;
                    } else {
                        z31 = sharedPreferences2.getBoolean("home_follow_library_sort", z29);
                    }
                    boolean zI2 = I(mutableState101);
                    boolean zBooleanValue115 = ((Boolean) mutableState102.getValue()).booleanValue();
                    boolean zJ4 = J(mutableState103);
                    boolean enableDetailOverviewFocus2 = userPreferences.getEnableDetailOverviewFocus();
                    boolean enableDetailExternalPlayer2 = userPreferences.getEnableDetailExternalPlayer();
                    boolean zBooleanValue116 = ((Boolean) mutableState104.getValue()).booleanValue();
                    boolean zBooleanValue117 = ((Boolean) state4.getValue()).booleanValue();
                    boolean zK2 = K(mutableState105);
                    AggregateResultSortMode aggregateResultSortMode2 = userPreferences.getAggregateResultSortMode();
                    boolean zBooleanValue118 = ((Boolean) state7.getValue()).booleanValue();
                    zChangedInstance16 = composer8.changedInstance(userPreferences);
                    boolean z54 = z31;
                    objRememberedValue80 = composer8.rememberedValue();
                    if (zChangedInstance16) {
                        objRememberedValue80 = new i6(userPreferences, 14);
                        composer8.updateRememberedValue(objRememberedValue80);
                    } else {
                        objRememberedValue80 = new i6(userPreferences, 14);
                        composer8.updateRememberedValue(objRememberedValue80);
                    }
                    Function1 function519 = (KFunction) objRememberedValue80;
                    zChangedInstance17 = composer8.changedInstance(userPreferences);
                    objRememberedValue81 = composer8.rememberedValue();
                    if (zChangedInstance17) {
                        objRememberedValue81 = new i6(userPreferences, 15);
                        composer8.updateRememberedValue(objRememberedValue81);
                    } else {
                        objRememberedValue81 = new i6(userPreferences, 15);
                        composer8.updateRememberedValue(objRememberedValue81);
                    }
                    Function1 function610 = (KFunction) objRememberedValue81;
                    objRememberedValue82 = composer8.rememberedValue();
                    if (objRememberedValue82 == companion5.getEmpty()) {
                        objRememberedValue82 = new yo0(0, mutableState47, mutableState45);
                        composer8.updateRememberedValue(objRememberedValue82);
                    }
                    function12 = (Function0) objRememberedValue82;
                    zChangedInstance18 = composer8.changedInstance(userPreferences) | composer8.changed(mutableState43) | composer8.changedInstance(fs0Var3);
                    mutableState76 = mutableState47;
                    Object objRememberedValue184 = composer8.rememberedValue();
                    if (zChangedInstance18) {
                        final fs0 fs0Var17 = fs0Var3;
                        final MutableState mutableState128 = mutableState45;
                        final MutableState mutableState129 = mutableState43;
                        final MutableState state110 = state4;
                        final MutableState state111 = state;
                        obj5 = new Function15() { // from class: zo0
                            public final Object invoke(Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object obj13, Object obj14, Object obj15, Object obj16, Object obj17, Object obj18, Object obj19, Object obj20, Object obj21, Object obj22) throws NoSuchAlgorithmException {
                                Boolean bool12 = (Boolean) obj8;
                                boolean zBooleanValue119 = bool12.booleanValue();
                                Boolean bool13 = (Boolean) obj9;
                                boolean zBooleanValue28 = bool13.booleanValue();
                                Boolean bool14 = (Boolean) obj10;
                                boolean zBooleanValue29 = bool14.booleanValue();
                                Boolean bool15 = (Boolean) obj11;
                                boolean zBooleanValue210 = bool15.booleanValue();
                                Boolean bool16 = (Boolean) obj12;
                                boolean zBooleanValue211 = bool16.booleanValue();
                                Boolean bool17 = (Boolean) obj13;
                                boolean zBooleanValue212 = bool17.booleanValue();
                                Boolean bool18 = (Boolean) obj14;
                                boolean zBooleanValue213 = bool18.booleanValue();
                                boolean zBooleanValue214 = ((Boolean) obj15).booleanValue();
                                boolean zBooleanValue215 = ((Boolean) obj16).booleanValue();
                                Boolean bool19 = (Boolean) obj17;
                                boolean zBooleanValue216 = bool19.booleanValue();
                                Boolean bool110 = (Boolean) obj18;
                                boolean zBooleanValue217 = bool110.booleanValue();
                                Boolean bool111 = (Boolean) obj19;
                                boolean zBooleanValue30 = bool111.booleanValue();
                                Boolean bool112 = (Boolean) obj20;
                                boolean zBooleanValue31 = bool112.booleanValue();
                                Boolean bool113 = (Boolean) obj21;
                                boolean zBooleanValue32 = bool113.booleanValue();
                                Boolean bool114 = (Boolean) obj22;
                                boolean zBooleanValue33 = bool114.booleanValue();
                                mutableState95.setValue(bool12);
                                mutableState96.setValue(bool13);
                                mutableState97.setValue(bool14);
                                mutableState98.setValue(bool15);
                                mutableState99.setValue(bool16);
                                mutableState100.setValue(bool17);
                                state111.setValue(bool18);
                                mutableState101.setValue(bool19);
                                mutableState102.setValue(bool110);
                                mutableState103.setValue(bool111);
                                mutableState104.setValue(bool112);
                                state110.setValue(bool113);
                                mutableState105.setValue(bool114);
                                UserPreferences userPreferences3 = userPreferences;
                                userPreferences3.saveEnableAggregateSearch(zBooleanValue119);
                                userPreferences3.saveEnableAggregateSearchInServerSearch(zBooleanValue28);
                                userPreferences3.saveDisableTopNavFocusRefresh(zBooleanValue29);
                                userPreferences3.saveEnableTransparentTopNavButtons(zBooleanValue210);
                                userPreferences3.saveEnableClickableLibrarySectionTitle(zBooleanValue211);
                                userPreferences3.saveEnableLibraryRowMoreButton(zBooleanValue212);
                                userPreferences3.saveEnableHomeUnplayedOnly(zBooleanValue213);
                                MutableState mutableState1210 = mutableState129;
                                String strD = xu.D(((ir0) mutableState1210.getValue()).a);
                                xu.S(zBooleanValue214);
                                xu.R(zBooleanValue215);
                                userPreferences3.saveEnablePlaybackStartupPoster(zBooleanValue216);
                                userPreferences3.saveEnableAggregateSearchVersionTags(zBooleanValue217);
                                userPreferences3.saveEnableDetailAggregateVersionRail(zBooleanValue30);
                                userPreferences3.saveEnableHomeBackFocusServerButton(zBooleanValue31);
                                userPreferences3.saveEnableAppUpdateCheckOnStartup(zBooleanValue32);
                                userPreferences3.saveEnableSpecialFeatures(zBooleanValue33);
                                if (!Intrinsics.areEqual(strD, xu.D(((ir0) mutableState1210.getValue()).a))) {
                                    fs0Var17.G(false, false);
                                }
                                mutableState76.setValue(Boolean.FALSE);
                                Unit unit9 = Unit.INSTANCE;
                                mutableState128.setValue(Boolean.TRUE);
                                return Unit.INSTANCE;
                            }
                        };
                        mutableState58 = mutableState95;
                        mutableState59 = mutableState96;
                        mutableState62 = mutableState99;
                        mutableState63 = mutableState100;
                        mutableState64 = mutableState101;
                        mutableState65 = mutableState102;
                        mutableState66 = mutableState103;
                        mutableState67 = mutableState105;
                        mutableState77 = mutableState129;
                        mutableState78 = mutableState128;
                        mutableState60 = mutableState97;
                        mutableState61 = mutableState98;
                        composer8.updateRememberedValue(obj5);
                    } else {
                        final fs0 fs0Var18 = fs0Var3;
                        final MutableState mutableState1210 = mutableState45;
                        final MutableState mutableState1211 = mutableState43;
                        final MutableState state112 = state4;
                        final MutableState state113 = state;
                        obj5 = new Function15() { // from class: zo0
                            public final Object invoke(Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object obj13, Object obj14, Object obj15, Object obj16, Object obj17, Object obj18, Object obj19, Object obj20, Object obj21, Object obj22) throws NoSuchAlgorithmException {
                                Boolean bool12 = (Boolean) obj8;
                                boolean zBooleanValue119 = bool12.booleanValue();
                                Boolean bool13 = (Boolean) obj9;
                                boolean zBooleanValue28 = bool13.booleanValue();
                                Boolean bool14 = (Boolean) obj10;
                                boolean zBooleanValue29 = bool14.booleanValue();
                                Boolean bool15 = (Boolean) obj11;
                                boolean zBooleanValue210 = bool15.booleanValue();
                                Boolean bool16 = (Boolean) obj12;
                                boolean zBooleanValue211 = bool16.booleanValue();
                                Boolean bool17 = (Boolean) obj13;
                                boolean zBooleanValue212 = bool17.booleanValue();
                                Boolean bool18 = (Boolean) obj14;
                                boolean zBooleanValue213 = bool18.booleanValue();
                                boolean zBooleanValue214 = ((Boolean) obj15).booleanValue();
                                boolean zBooleanValue215 = ((Boolean) obj16).booleanValue();
                                Boolean bool19 = (Boolean) obj17;
                                boolean zBooleanValue216 = bool19.booleanValue();
                                Boolean bool110 = (Boolean) obj18;
                                boolean zBooleanValue217 = bool110.booleanValue();
                                Boolean bool111 = (Boolean) obj19;
                                boolean zBooleanValue30 = bool111.booleanValue();
                                Boolean bool112 = (Boolean) obj20;
                                boolean zBooleanValue31 = bool112.booleanValue();
                                Boolean bool113 = (Boolean) obj21;
                                boolean zBooleanValue32 = bool113.booleanValue();
                                Boolean bool114 = (Boolean) obj22;
                                boolean zBooleanValue33 = bool114.booleanValue();
                                mutableState95.setValue(bool12);
                                mutableState96.setValue(bool13);
                                mutableState97.setValue(bool14);
                                mutableState98.setValue(bool15);
                                mutableState99.setValue(bool16);
                                mutableState100.setValue(bool17);
                                state113.setValue(bool18);
                                mutableState101.setValue(bool19);
                                mutableState102.setValue(bool110);
                                mutableState103.setValue(bool111);
                                mutableState104.setValue(bool112);
                                state112.setValue(bool113);
                                mutableState105.setValue(bool114);
                                UserPreferences userPreferences3 = userPreferences;
                                userPreferences3.saveEnableAggregateSearch(zBooleanValue119);
                                userPreferences3.saveEnableAggregateSearchInServerSearch(zBooleanValue28);
                                userPreferences3.saveDisableTopNavFocusRefresh(zBooleanValue29);
                                userPreferences3.saveEnableTransparentTopNavButtons(zBooleanValue210);
                                userPreferences3.saveEnableClickableLibrarySectionTitle(zBooleanValue211);
                                userPreferences3.saveEnableLibraryRowMoreButton(zBooleanValue212);
                                userPreferences3.saveEnableHomeUnplayedOnly(zBooleanValue213);
                                MutableState mutableState1212 = mutableState1211;
                                String strD = xu.D(((ir0) mutableState1212.getValue()).a);
                                xu.S(zBooleanValue214);
                                xu.R(zBooleanValue215);
                                userPreferences3.saveEnablePlaybackStartupPoster(zBooleanValue216);
                                userPreferences3.saveEnableAggregateSearchVersionTags(zBooleanValue217);
                                userPreferences3.saveEnableDetailAggregateVersionRail(zBooleanValue30);
                                userPreferences3.saveEnableHomeBackFocusServerButton(zBooleanValue31);
                                userPreferences3.saveEnableAppUpdateCheckOnStartup(zBooleanValue32);
                                userPreferences3.saveEnableSpecialFeatures(zBooleanValue33);
                                if (!Intrinsics.areEqual(strD, xu.D(((ir0) mutableState1212.getValue()).a))) {
                                    fs0Var18.G(false, false);
                                }
                                mutableState76.setValue(Boolean.FALSE);
                                Unit unit9 = Unit.INSTANCE;
                                mutableState1210.setValue(Boolean.TRUE);
                                return Unit.INSTANCE;
                            }
                        };
                        mutableState58 = mutableState95;
                        mutableState59 = mutableState96;
                        mutableState62 = mutableState99;
                        mutableState63 = mutableState100;
                        mutableState64 = mutableState101;
                        mutableState65 = mutableState102;
                        mutableState66 = mutableState103;
                        mutableState67 = mutableState105;
                        mutableState77 = mutableState1211;
                        mutableState78 = mutableState1210;
                        mutableState60 = mutableState97;
                        mutableState61 = mutableState98;
                        composer8.updateRememberedValue(obj5);
                    }
                    Function15 function117 = (Function15) obj5;
                    Function1 function611 = function519;
                    Function1 function612 = function610;
                    zChangedInstance19 = composer8.changedInstance(userPreferences);
                    objRememberedValue83 = composer8.rememberedValue();
                    if (zChangedInstance19) {
                        function13 = function12;
                        if (objRememberedValue83 == companion5.getEmpty()) {
                        }
                        Function1 function613 = (Function1) objRememberedValue83;
                        Composer composer310 = composer8;
                        mutableState57 = mutableState77;
                        mutableState56 = mutableState78;
                        ew0.d(zBooleanValue26, zBooleanValue27, zBooleanValue110, zBooleanValue111, zBooleanValue112, zBooleanValue113, zBooleanValue114, z30, z54, zI2, zBooleanValue115, zJ4, enableDetailOverviewFocus2, enableDetailExternalPlayer2, zBooleanValue116, zBooleanValue117, zK2, aggregateResultSortMode2, zBooleanValue118, function13, function117, function611, function612, function613, composer310, 0, 805306368, 0);
                        composer9 = composer310;
                        composer9.endReplaceGroup();
                    } else {
                        function13 = function12;
                    }
                    objRememberedValue83 = new ap0(userPreferences, 0);
                    composer8.updateRememberedValue(objRememberedValue83);
                    Function1 function614 = (Function1) objRememberedValue83;
                    Composer composer311 = composer8;
                    mutableState57 = mutableState77;
                    mutableState56 = mutableState78;
                    ew0.d(zBooleanValue26, zBooleanValue27, zBooleanValue110, zBooleanValue111, zBooleanValue112, zBooleanValue113, zBooleanValue114, z30, z54, zI2, zBooleanValue115, zJ4, enableDetailOverviewFocus2, enableDetailExternalPlayer2, zBooleanValue116, zBooleanValue117, zK2, aggregateResultSortMode2, zBooleanValue118, function13, function117, function611, function612, function614, composer311, 0, 805306368, 0);
                    composer9 = composer311;
                    composer9.endReplaceGroup();
                } else {
                    composer9 = composer8;
                    mutableState56 = mutableState45;
                    mutableState57 = mutableState43;
                    mutableState55 = mutableState55;
                    userPreferences = userPreferences;
                    mutableState58 = mutableState95;
                    mutableState59 = mutableState96;
                    mutableState60 = mutableState97;
                    mutableState61 = mutableState98;
                    mutableState62 = mutableState99;
                    mutableState63 = mutableState100;
                    mutableState64 = mutableState101;
                    mutableState65 = mutableState102;
                    mutableState66 = mutableState103;
                    mutableState67 = mutableState105;
                    composer9.startReplaceGroup(1433660985);
                    composer9.endReplaceGroup();
                }
                if (((Boolean) mutableState49.getValue()).booleanValue()) {
                    composer9.startReplaceGroup(1433717436);
                    String qrNetworkInterfaceName2 = userPreferences.getQrNetworkInterfaceName();
                    objRememberedValue78 = composer9.rememberedValue();
                    if (objRememberedValue78 == companion5.getEmpty()) {
                        objRememberedValue78 = new yo0(1, mutableState49, mutableState56);
                        composer9.updateRememberedValue(objRememberedValue78);
                    }
                    Function0 function615 = (Function0) objRememberedValue78;
                    zChangedInstance15 = composer9.changedInstance(userPreferences);
                    objRememberedValue79 = composer9.rememberedValue();
                    if (zChangedInstance15) {
                        z25 = false;
                        objRememberedValue79 = new bp0(userPreferences, mutableState49, mutableState56, 0);
                        composer9.updateRememberedValue(objRememberedValue79);
                    } else {
                        z25 = false;
                        objRememberedValue79 = new bp0(userPreferences, mutableState49, mutableState56, 0);
                        composer9.updateRememberedValue(objRememberedValue79);
                    }
                    ot1.b(qrNetworkInterfaceName2, function615, (Function1) objRememberedValue79, composer9, 48);
                    composer9.endReplaceGroup();
                } else {
                    z25 = false;
                    composer9.startReplaceGroup(1434145081);
                    composer9.endReplaceGroup();
                }
                if (((Boolean) mutableState51.getValue()).booleanValue()) {
                    composer9.startReplaceGroup(1434189318);
                    objRememberedValue77 = composer9.rememberedValue();
                    if (objRememberedValue77 == companion5.getEmpty()) {
                        objRememberedValue77 = new yo0(2, mutableState51, mutableState56);
                        composer9.updateRememberedValue(objRememberedValue77);
                    }
                    an.a((Function0) objRememberedValue77, composer9, 6);
                    composer9.endReplaceGroup();
                } else {
                    composer9.startReplaceGroup(1434330585);
                    composer9.endReplaceGroup();
                }
                if (((Boolean) mutableState53.getValue()).booleanValue()) {
                    composer9.startReplaceGroup(1434388214);
                    List listR2 = r(stateCollectAsState);
                    String strS2 = s(stateCollectAsState2);
                    objRememberedValue73 = composer9.rememberedValue();
                    if (objRememberedValue73 == companion5.getEmpty()) {
                        mutableState68 = mutableState53;
                        mutableState69 = mutableState27;
                        objRememberedValue73 = new yo0(3, mutableState69, mutableState68);
                        composer9.updateRememberedValue(objRememberedValue73);
                    } else {
                        mutableState68 = mutableState53;
                        mutableState69 = mutableState27;
                    }
                    Function0 function616 = (Function0) objRememberedValue73;
                    coroutineScope2 = coroutineScope;
                    v52Var3 = v52Var2;
                    zChangedInstance13 = composer9.changedInstance(coroutineScope2) | composer9.changedInstance(v52Var3);
                    objRememberedValue74 = composer9.rememberedValue();
                    if (zChangedInstance13) {
                        objRememberedValue74 = new i4(coroutineScope2, v52Var3, 2);
                        composer9.updateRememberedValue(objRememberedValue74);
                    } else {
                        objRememberedValue74 = new i4(coroutineScope2, v52Var3, 2);
                        composer9.updateRememberedValue(objRememberedValue74);
                    }
                    Function1 function617 = (Function1) objRememberedValue74;
                    zChangedInstance14 = composer9.changedInstance(coroutineScope2) | composer9.changedInstance(v52Var3);
                    objRememberedValue75 = composer9.rememberedValue();
                    if (zChangedInstance14) {
                        objRememberedValue75 = new i4(coroutineScope2, v52Var3, 3);
                        composer9.updateRememberedValue(objRememberedValue75);
                    } else {
                        objRememberedValue75 = new i4(coroutineScope2, v52Var3, 3);
                        composer9.updateRememberedValue(objRememberedValue75);
                    }
                    Function1 function618 = (Function1) objRememberedValue75;
                    objRememberedValue76 = composer9.rememberedValue();
                    if (objRememberedValue76 == companion5.getEmpty()) {
                        objRememberedValue76 = new yo0(4, mutableState68, mutableState56);
                        composer9.updateRememberedValue(objRememberedValue76);
                    }
                    Composer composer312 = composer9;
                    z26 = false;
                    n52.d(listR2, strS2, function616, function617, function618, (Function0) objRememberedValue76, composer312, 196992);
                    composer10 = composer312;
                    composer10.endReplaceGroup();
                } else {
                    composer10 = composer9;
                    z26 = z25;
                    mutableState68 = mutableState53;
                    mutableState69 = mutableState27;
                    coroutineScope2 = coroutineScope;
                    composer10.startReplaceGroup(1435036889);
                    composer10.endReplaceGroup();
                }
                if (((Boolean) mutableState50.getValue()).booleanValue()) {
                    composer10.startReplaceGroup(1435085063);
                    ProxyConfig proxyConfigN5 = N(stateCollectAsState18);
                    objRememberedValue72 = composer10.rememberedValue();
                    if (objRememberedValue72 == companion5.getEmpty()) {
                        mutableState74 = mutableState50;
                        objRememberedValue72 = new yo0(5, mutableState74, mutableState56);
                        composer10.updateRememberedValue(objRememberedValue72);
                    } else {
                        mutableState74 = mutableState50;
                    }
                    Function0 function619 = (Function0) objRememberedValue72;
                    mutableState75 = mutableState56;
                    zChangedInstance12 = composer10.changedInstance(coroutineScope2) | composer10.changedInstance(proxySettings) | composer10.changedInstance(context3);
                    Object objRememberedValue185 = composer10.rememberedValue();
                    if (zChangedInstance12) {
                        j4Var = new j4(coroutineScope2, proxySettings, context3, mutableState74, mutableState75, 1);
                        mutableState56 = mutableState75;
                        composer10.updateRememberedValue(j4Var);
                    } else {
                        j4Var = new j4(coroutineScope2, proxySettings, context3, mutableState74, mutableState75, 1);
                        mutableState56 = mutableState75;
                        composer10.updateRememberedValue(j4Var);
                    }
                    Composer composer313 = composer10;
                    xs1.c(proxyConfigN5, proxyConfigServerManager, function619, (Function1) j4Var, composer313, 384);
                    composer11 = composer313;
                    composer11.endReplaceGroup();
                } else {
                    composer11 = composer10;
                    composer11.startReplaceGroup(1435625145);
                    composer11.endReplaceGroup();
                }
                if (((Boolean) mutableState69.getValue()).booleanValue()) {
                    composer11.startReplaceGroup(1435670560);
                    String str10 = (String) mutableState18.getValue();
                    String str11 = (String) mutableState18.getValue();
                    if (str11 != null) {
                    }
                    boolean zBooleanValue119 = ((Boolean) mutableState19.getValue()).booleanValue();
                    objRememberedValue71 = composer11.rememberedValue();
                    if (objRememberedValue71 == companion5.getEmpty()) {
                        objRememberedValue71 = new yo0(6, mutableState69, mutableState68);
                        composer11.updateRememberedValue(objRememberedValue71);
                    }
                    Composer composer314 = composer11;
                    a62.a(str10, z52, zBooleanValue119, (Function0) objRememberedValue71, composer314, 3072);
                    composer11 = composer314;
                    composer11.endReplaceGroup();
                } else {
                    composer11.startReplaceGroup(1435971353);
                    composer11.endReplaceGroup();
                }
                if (((Boolean) mutableState54.getValue()).booleanValue()) {
                    composer11.startReplaceGroup(1436031865);
                    fb1 fb1VarV2 = v(stateCollectAsState3);
                    objRememberedValue69 = composer11.rememberedValue();
                    if (objRememberedValue69 == companion5.getEmpty()) {
                        mutableState73 = mutableState54;
                        objRememberedValue69 = new yo0(7, mutableState73, mutableState56);
                        composer11.updateRememberedValue(objRememberedValue69);
                    } else {
                        mutableState73 = mutableState54;
                    }
                    Function0 function74 = (Function0) objRememberedValue69;
                    zChangedInstance11 = composer11.changedInstance(cc1Var);
                    objRememberedValue70 = composer11.rememberedValue();
                    if (zChangedInstance11) {
                        objRememberedValue70 = new k4(cc1Var, mutableState73, mutableState56, 1);
                        composer11.updateRememberedValue(objRememberedValue70);
                    } else {
                        objRememberedValue70 = new k4(cc1Var, mutableState73, mutableState56, 1);
                        composer11.updateRememberedValue(objRememberedValue70);
                    }
                    Composer composer315 = composer11;
                    kb1.c(fb1VarV2, onlineSubtitleConfigServerManager, function74, (Function1) objRememberedValue70, composer315, 384);
                    composer12 = composer315;
                    composer12.endReplaceGroup();
                } else {
                    composer12 = composer11;
                    composer12.startReplaceGroup(1436493145);
                    composer12.endReplaceGroup();
                }
                if (((Boolean) mutableState14.getValue()).booleanValue()) {
                    composer12.startReplaceGroup(1436544171);
                    ug2 ug2VarW2 = w(stateCollectAsState4);
                    objRememberedValue66 = composer12.rememberedValue();
                    if (objRememberedValue66 == companion5.getEmpty()) {
                        objRememberedValue66 = new yo0(8, mutableState14, mutableState56);
                        composer12.updateRememberedValue(objRememberedValue66);
                    }
                    Function0 function75 = (Function0) objRememberedValue66;
                    zChangedInstance10 = composer12.changedInstance(oh2Var);
                    objRememberedValue67 = composer12.rememberedValue();
                    if (zChangedInstance10) {
                        objRememberedValue67 = new l4(oh2Var, 1);
                        composer12.updateRememberedValue(objRememberedValue67);
                    } else {
                        objRememberedValue67 = new l4(oh2Var, 1);
                        composer12.updateRememberedValue(objRememberedValue67);
                    }
                    Function1 function76 = (Function1) objRememberedValue67;
                    serverPreferences6 = serverPreferences5;
                    boolean zChangedInstance318 = composer12.changedInstance(userPreferences) | composer12.changedInstance(serverPreferences6) | composer12.changed(mutableState57) | composer12.changedInstance(context3);
                    if ((i14 & 3670016) == 1048576) {
                        z27 = true;
                    } else {
                        z27 = z26;
                    }
                    boolean zChangedInstance319 = zChangedInstance318 | z27 | composer12.changedInstance(fs0Var3);
                    if ((i14 & 458752) == 131072) {
                        z26 = true;
                    }
                    z28 = zChangedInstance319 | z26;
                    objRememberedValue68 = composer12.rememberedValue();
                    if (z28) {
                        tq0 tq0Var2 = new tq0(serverPreferences6, context3, function0, fs0Var3, userPreferences, mutableState58, mutableState59, mutableState60, mutableState61, mutableState62, mutableState63, state, mutableState64, mutableState65, mutableState66, mutableState104, state4, state7, mutableState67, mutableState57, function6, mutableState31, state8);
                        composer12.updateRememberedValue(tq0Var2);
                        objRememberedValue68 = tq0Var2;
                    } else {
                        tq0 tq0Var3 = new tq0(serverPreferences6, context3, function0, fs0Var3, userPreferences, mutableState58, mutableState59, mutableState60, mutableState61, mutableState62, mutableState63, state, mutableState64, mutableState65, mutableState66, mutableState104, state4, state7, mutableState67, mutableState57, function6, mutableState31, state8);
                        composer12.updateRememberedValue(tq0Var3);
                        objRememberedValue68 = tq0Var3;
                    }
                    Composer composer316 = composer12;
                    ah2.f(ug2VarW2, nh2Var, webDavSyncConfigServerManager, function75, function76, (KFunction) objRememberedValue68, composer316, 3072);
                    composer2 = composer316;
                    composer2.endReplaceGroup();
                } else {
                    composer2 = composer12;
                    mutableState56 = mutableState56;
                    composer2.startReplaceGroup(1437018905);
                    composer2.endReplaceGroup();
                }
                if (((Boolean) mutableState55.getValue()).booleanValue()) {
                    composer2.startReplaceGroup(1437064258);
                    db2 db2VarX2 = x(stateCollectAsState5);
                    objRememberedValue64 = composer2.rememberedValue();
                    if (objRememberedValue64 == companion5.getEmpty()) {
                        objRememberedValue64 = new yo0(9, mutableState55, mutableState56);
                        composer2.updateRememberedValue(objRememberedValue64);
                    }
                    Function0 function77 = (Function0) objRememberedValue64;
                    vc2Var2 = vc2Var;
                    zChangedInstance9 = composer2.changedInstance(vc2Var2);
                    objRememberedValue65 = composer2.rememberedValue();
                    if (zChangedInstance9) {
                        objRememberedValue65 = new n4(vc2Var2, 1);
                        composer2.updateRememberedValue(objRememberedValue65);
                    } else {
                        objRememberedValue65 = new n4(vc2Var2, 1);
                        composer2.updateRememberedValue(objRememberedValue65);
                    }
                    Composer composer317 = composer2;
                    cd2.b(db2VarX2, sc2Var, function77, (Function1) objRememberedValue65, composer317, 384);
                    composer2 = composer317;
                    composer2.endReplaceGroup();
                } else {
                    composer2.startReplaceGroup(1437393881);
                    composer2.endReplaceGroup();
                }
                if (((Boolean) mutableState16.getValue()).booleanValue()) {
                    composer2.startReplaceGroup(1437433840);
                    objRememberedValue63 = composer2.rememberedValue();
                    if (objRememberedValue63 == companion5.getEmpty()) {
                        objRememberedValue63 = new hp0(10);
                        composer2.updateRememberedValue(objRememberedValue63);
                    }
                    Composer composer318 = composer2;
                    AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue63, st.g(), (Modifier) null, (Function2) null, (Function2) null, st.c(), st.e(), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer318, 1769526, 0, 16284);
                    composer2 = composer318;
                    composer2.endReplaceGroup();
                } else {
                    composer2.startReplaceGroup(1437873017);
                    composer2.endReplaceGroup();
                }
                if (((Boolean) mutableState17.getValue()).booleanValue()) {
                    composer2.startReplaceGroup(1437925314);
                    objRememberedValue62 = composer2.rememberedValue();
                    if (objRememberedValue62 == companion5.getEmpty()) {
                        mutableState72 = mutableState17;
                        i26 = 11;
                        objRememberedValue62 = new yo0(11, mutableState72, mutableState56);
                        composer2.updateRememberedValue(objRememberedValue62);
                    } else {
                        mutableState72 = mutableState17;
                        i26 = 11;
                    }
                    i27 = 54;
                    Composer composer319 = composer2;
                    AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue62, ComposableLambdaKt.rememberComposableLambda(2080795661, true, new o4(2, mutableState72, mutableState56), composer2, 54), (Modifier) null, (Function2) null, (Function2) null, st.j(), st.f(), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer319, 1769526, 0, 16284);
                    composer2 = composer319;
                    composer2.endReplaceGroup();
                } else {
                    i26 = 11;
                    i27 = 54;
                    composer2.startReplaceGroup(1438347193);
                    composer2.endReplaceGroup();
                }
                if (t(mutableState20) != null) {
                    composer2.startReplaceGroup(1438399180);
                    objRememberedValue61 = composer2.rememberedValue();
                    if (objRememberedValue61 == companion5.getEmpty()) {
                        mutableState71 = mutableState20;
                        objRememberedValue61 = new x50(20, mutableState71);
                        composer2.updateRememberedValue(objRememberedValue61);
                    } else {
                        mutableState71 = mutableState20;
                    }
                    Composer composer42 = composer2;
                    AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue61, ComposableLambdaKt.rememberComposableLambda(-1287416882, true, new c2(10, mutableState71), composer2, i27), (Modifier) null, (Function2) null, (Function2) null, st.h(), ComposableLambdaKt.rememberComposableLambda(1035531219, true, new c2(i26, mutableState71), composer2, i27), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer42, 1769526, 0, 16284);
                    composer2 = composer42;
                    composer2.endReplaceGroup();
                } else {
                    composer2.startReplaceGroup(1438780697);
                    composer2.endReplaceGroup();
                }
                if (u(mutableState21) != null) {
                    composer2.startReplaceGroup(1438831537);
                    objRememberedValue60 = composer2.rememberedValue();
                    if (objRememberedValue60 == companion5.getEmpty()) {
                        mutableState70 = mutableState21;
                        objRememberedValue60 = new x50(21, mutableState70);
                        composer2.updateRememberedValue(objRememberedValue60);
                    } else {
                        mutableState70 = mutableState21;
                    }
                    Composer composer43 = composer2;
                    AndroidAlertDialog_androidKt.AlertDialog-Oix01E0((Function0) objRememberedValue60, ComposableLambdaKt.rememberComposableLambda(-360662129, true, new c2(12, mutableState70), composer2, i27), (Modifier) null, (Function2) null, (Function2) null, st.b(), ComposableLambdaKt.rememberComposableLambda(1962285972, true, new c2(13, mutableState70), composer2, i27), (Shape) null, 0L, 0L, 0L, 0L, 0.0f, (DialogProperties) null, composer43, 1769526, 0, 16284);
                    composer2 = composer43;
                    composer2.endReplaceGroup();
                } else {
                    composer2.startReplaceGroup(1439208249);
                    composer2.endReplaceGroup();
                }
                composer2.endReplaceGroup();
            } else {
                composer2.startReplaceGroup(1439214201);
                composer2.endReplaceGroup();
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composer2 = composerStartRestartGroup;
            composer2.skipToGroupEnd();
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new zp0(function1, function2, function3, function4, function5, function6, function0, function7, function8, function9, ir0Var, fs0Var, z, i, i2));
        }
    }

    public static final boolean g(ServerPreferences serverPreferences, Context context, fs0 fs0Var, Function1 function1, MutableState mutableState, MutableState mutableState2, MutableState mutableState3, ServerConfig serverConfig, boolean z) {
        ServerConfig effectiveServerConfig = serverConfig.getEffectiveServerConfig();
        String accessToken = effectiveServerConfig.getAccessToken();
        String userId = effectiveServerConfig.getUserId();
        if (accessToken == null || StringsKt.isBlank(accessToken) || userId == null || StringsKt.isBlank(userId)) {
            return false;
        }
        if (z) {
            serverPreferences.setLastUsedServerId(serverConfig.getId());
        }
        RetrofitClient retrofitClient = RetrofitClient.INSTANCE;
        retrofitClient.initialize(effectiveServerConfig.getFullUrl());
        retrofitClient.setAuthToken(accessToken, userId);
        retrofitClient.setDeviceId(DeviceIdManager.INSTANCE.getDeviceId(context));
        retrofitClient.setTrustAllCerts(effectiveServerConfig.getTrustAllCerts());
        Proxy proxy = ct1.a;
        ct1.c(effectiveServerConfig.getDirectOnly());
        mutableState.setValue(new ir0(serverConfig.getId(), effectiveServerConfig.getAlias(), effectiveServerConfig.getDirectOnly(), serverConfig.getCustomIconPath(), serverConfig.getActiveBackupRouteId()));
        String id = serverConfig.getId();
        context.getClass();
        if (fs0Var.R == null) {
            Context applicationContext = context.getApplicationContext();
            applicationContext.getClass();
            fs0Var.R = new ha(applicationContext);
        }
        fs0Var.S = id;
        function1.invoke(serverConfig.getId());
        to0 to0Var = to0.a;
        mutableState2.setValue(to0Var);
        p(mutableState3, true);
        fs0Var.i();
        fs0Var.R(to0Var);
        return true;
    }

    public static final void h(MutableState mutableState, MutableState mutableState2, MutableState mutableState3) {
        Job job = (Job) mutableState.getValue();
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        mutableState.setValue((Object) null);
        mutableState2.setValue(false);
        mutableState3.setValue(CollectionsKt.emptyList());
    }

    public static final void i(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    public static final boolean j(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    public static final void k(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    public static final to0 l(State state) {
        return (to0) state.getValue();
    }

    public static final List m(MutableState mutableState) {
        return (List) mutableState.getValue();
    }

    public static final void n(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    public static final h12 o(MutableState mutableState) {
        return (h12) mutableState.getValue();
    }

    public static final void p(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    public static final void q(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    public static final List r(State state) {
        return (List) state.getValue();
    }

    public static final String s(State state) {
        return (String) state.getValue();
    }

    public static final String t(MutableState mutableState) {
        return (String) mutableState.getValue();
    }

    public static final String u(MutableState mutableState) {
        return (String) mutableState.getValue();
    }

    public static final fb1 v(State state) {
        return (fb1) state.getValue();
    }

    public static final ug2 w(State state) {
        return (ug2) state.getValue();
    }

    public static final db2 x(State state) {
        return (db2) state.getValue();
    }

    public static final boolean y(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    public static final int z(MutableState mutableState) {
        return ((Number) mutableState.getValue()).intValue();
    }
}
