package com.dh.myembyapp.data.model;

import androidx.media3.container.NalUnitUtil;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.extractor.text.ttml.TtmlNode;
import androidx.profileinstaller.ProfileVerifier;
import com.badlogic.gdx.graphics.GL20;
import com.google.gson.annotations.SerializedName;
import defpackage.bc0;
import defpackage.f22;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: loaded from: D:\Ai\Github\ChaiSync-GH\build\decoded\classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\bT\b\u0087\b\u0018\u00002\u00020\u0001B\u0083\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0012\u0012\u0010\b\u0002\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0010¢\u0006\u0004\b \u0010!J\u0006\u0010I\u001a\u00020\u0003J\u0006\u0010J\u001a\u00020\u0003J\u0006\u0010K\u001a\u00020\u0012J\f\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018J\b\u0010M\u001a\u0004\u0018\u00010\u0019J\u0006\u0010N\u001a\u00020\u0003J\u0006\u0010O\u001a\u00020\u0000J\t\u0010P\u001a\u00020\u0003HÆ\u0003J\t\u0010Q\u001a\u00020\u0003HÆ\u0003J\t\u0010R\u001a\u00020\u0003HÆ\u0003J\t\u0010S\u001a\u00020\u0003HÆ\u0003J\t\u0010T\u001a\u00020\bHÆ\u0003J\u000b\u0010U\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010V\u001a\u00020\u0003HÆ\u0003J\t\u0010W\u001a\u00020\u0003HÆ\u0003J\u000b\u0010X\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010Y\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010Z\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010[\u001a\u00020\u0010HÆ\u0003J\t\u0010\\\u001a\u00020\u0012HÆ\u0003J\t\u0010]\u001a\u00020\u0012HÆ\u0003J\t\u0010^\u001a\u00020\u0012HÆ\u0003J\t\u0010_\u001a\u00020\u0012HÆ\u0003J\t\u0010`\u001a\u00020\u0012HÆ\u0003J\u0011\u0010a\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u0018HÆ\u0003J\u000b\u0010b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010g\u001a\u00020\u0010HÆ\u0003J\u0093\u0002\u0010h\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u00122\b\b\u0002\u0010\u0015\u001a\u00020\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u00122\u0010\b\u0002\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00182\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u001f\u001a\u00020\u0010HÆ\u0001J\u0014\u0010i\u001a\u00020\u00122\b\u0010j\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010k\u001a\u00020\bHÖ\u0081\u0004J\n\u0010l\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010#R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010#R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010#R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0018\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010#R\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010#R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010#R \u0010\f\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010#\"\u0004\b-\u0010.R \u0010\r\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010#\"\u0004\b0\u0010.R \u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010#\"\u0004\b2\u0010.R\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001e\u0010\u0011\u001a\u00020\u00128\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u00107\"\u0004\b8\u00109R\u0016\u0010\u0013\u001a\u00020\u00128\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u00107R\u0016\u0010\u0014\u001a\u00020\u00128\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u00107R$\u0010\u0015\u001a\u00020\u00128\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b<\u0010=\u001a\u0004\b>\u00107\"\u0004\b?\u00109R\u0016\u0010\u0016\u001a\u00020\u00128\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b@\u00107R\u001e\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00188\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010#R\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bD\u0010#R\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bE\u0010#R\u0018\u0010\u001d\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bF\u0010#R\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010#R\u0016\u0010\u001f\u001a\u00020\u00108\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bH\u00104¨\u0006m"}, d2 = {"Lcom/dh/myembyapp/data/model/ServerConfig;", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ATTR_ID, TtmlNode.ANONYMOUS_REGION_ID, "alias", "protocol", "host", "port", TtmlNode.ANONYMOUS_REGION_ID, "path", "username", "password", "userId", "accessToken", "deviceId", "lastLoginTime", TtmlNode.ANONYMOUS_REGION_ID, "isVerified", TtmlNode.ANONYMOUS_REGION_ID, "directOnly", "enableStrmDirectPlay", "useEmbyPrefix", "trustAllCerts", "backupRoutes", TtmlNode.ANONYMOUS_REGION_ID, "Lcom/dh/myembyapp/data/model/BackupRouteConfig;", "activeBackupRouteId", "customIconPath", "customIconSourceUrl", "customIconName", "note", "lastPlaybackAccessTime", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JZZZZZLjava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getId", "()Ljava/lang/String;", "getAlias", "getProtocol", "getHost", "getPort", "()I", "getPath", "getUsername", "getPassword", "getUserId", "setUserId", "(Ljava/lang/String;)V", "getAccessToken", "setAccessToken", "getDeviceId", "setDeviceId", "getLastLoginTime", "()J", "setLastLoginTime", "(J)V", "()Z", "setVerified", "(Z)V", "getDirectOnly", "getEnableStrmDirectPlay", "getUseEmbyPrefix$annotations", "()V", "getUseEmbyPrefix", "setUseEmbyPrefix", "getTrustAllCerts", "getBackupRoutes", "()Ljava/util/List;", "getActiveBackupRouteId", "getCustomIconPath", "getCustomIconSourceUrl", "getCustomIconName", "getNote", "getLastPlaybackAccessTime", "getFullUrl", "getDisplayAddress", "isLoggedIn", "getBackupRoutesSafe", "getActiveBackupRoute", "getCurrentRouteDisplayName", "getEffectiveServerConfig", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component20", "component21", "component22", "component23", "component24", "copy", "equals", "other", "hashCode", "toString", "app"}, k = 1, mv = {2, 3, 0}, xi = NalUnitUtil.H265_NAL_UNIT_TYPE_UNSPECIFIED)
public final /* data */ class ServerConfig {
    public static final int $stable = 8;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("activeBackupRouteId")
    private final String activeBackupRouteId;

    @SerializedName("alias")
    private final String alias;

    @SerializedName("backupRoutes")
    private final List<BackupRouteConfig> backupRoutes;

    @SerializedName("customIconName")
    private final String customIconName;

    @SerializedName("customIconPath")
    private final String customIconPath;

    @SerializedName("customIconSourceUrl")
    private final String customIconSourceUrl;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("directOnly")
    private final boolean directOnly;

    @SerializedName("enableStrmDirectPlay")
    private final boolean enableStrmDirectPlay;

    @SerializedName("host")
    private final String host;

    @SerializedName(TtmlNode.ATTR_ID)
    private final String id;

    @SerializedName("isVerified")
    private boolean isVerified;

    @SerializedName("lastLoginTime")
    private long lastLoginTime;

    @SerializedName("lastPlaybackAccessTime")
    private final long lastPlaybackAccessTime;

    @SerializedName("note")
    private final String note;

    @SerializedName("password")
    private final String password;

    @SerializedName("path")
    private final String path;

    @SerializedName("port")
    private final int port;

    @SerializedName("protocol")
    private final String protocol;

    @SerializedName("trustAllCerts")
    private final boolean trustAllCerts;

    @SerializedName("useEmbyPrefix")
    private boolean useEmbyPrefix;

    @SerializedName("userId")
    private String userId;

    @SerializedName("username")
    private final String username;

    public /* synthetic */ ServerConfig(String str, String str2, String str3, String str4, int i, String str5, String str6, String str7, String str8, String str9, String str10, long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, List list, String str11, String str12, String str13, String str14, String str15, long j2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, i, (i2 & 32) != 0 ? TtmlNode.ANONYMOUS_REGION_ID : str5, str6, str7, (i2 & 256) != 0 ? null : str8, (i2 & 512) != 0 ? null : str9, (i2 & 1024) != 0 ? null : str10, (i2 & 2048) != 0 ? 0L : j, (i2 & 4096) != 0 ? false : z, (i2 & 8192) != 0 ? false : z2, (i2 & GL20.GL_COLOR_BUFFER_BIT) != 0 ? false : z3, (32768 & i2) != 0 ? true : z4, (65536 & i2) != 0 ? false : z5, (131072 & i2) != 0 ? CollectionsKt.emptyList() : list, (262144 & i2) != 0 ? null : str11, (524288 & i2) != 0 ? null : str12, (1048576 & i2) != 0 ? null : str13, (2097152 & i2) != 0 ? null : str14, (4194304 & i2) != 0 ? null : str15, (i2 & 8388608) != 0 ? 0L : j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ServerConfig copy$default(ServerConfig serverConfig, String str, String str2, String str3, String str4, int i, String str5, String str6, String str7, String str8, String str9, String str10, long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, List list, String str11, String str12, String str13, String str14, String str15, long j2, int i2, Object obj) {
        long j3;
        String str16;
        boolean z6;
        String str17 = (i2 & 1) != 0 ? serverConfig.id : str;
        String str18 = (i2 & 2) != 0 ? serverConfig.alias : str2;
        String str19 = (i2 & 4) != 0 ? serverConfig.protocol : str3;
        String str20 = (i2 & 8) != 0 ? serverConfig.host : str4;
        int i3 = (i2 & 16) != 0 ? serverConfig.port : i;
        String str21 = (i2 & 32) != 0 ? serverConfig.path : str5;
        String str22 = (i2 & 64) != 0 ? serverConfig.username : str6;
        String str23 = (i2 & 128) != 0 ? serverConfig.password : str7;
        String str24 = (i2 & 256) != 0 ? serverConfig.userId : str8;
        String str25 = (i2 & 512) != 0 ? serverConfig.accessToken : str9;
        String str26 = (i2 & 1024) != 0 ? serverConfig.deviceId : str10;
        long j4 = (i2 & 2048) != 0 ? serverConfig.lastLoginTime : j;
        boolean z7 = (i2 & 4096) != 0 ? serverConfig.isVerified : z;
        String str27 = str17;
        boolean z8 = (i2 & 8192) != 0 ? serverConfig.directOnly : z2;
        boolean z9 = (i2 & GL20.GL_COLOR_BUFFER_BIT) != 0 ? serverConfig.enableStrmDirectPlay : z3;
        boolean z10 = (i2 & GL20.GL_COVERAGE_BUFFER_BIT_NV) != 0 ? serverConfig.useEmbyPrefix : z4;
        boolean z11 = (i2 & 65536) != 0 ? serverConfig.trustAllCerts : z5;
        List list2 = (i2 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ) != 0 ? serverConfig.backupRoutes : list;
        String str28 = (i2 & ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_UNSUPPORTED_API_VERSION) != 0 ? serverConfig.activeBackupRouteId : str11;
        String str29 = (i2 & 524288) != 0 ? serverConfig.customIconPath : str12;
        String str30 = (i2 & ProgressiveMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES) != 0 ? serverConfig.customIconSourceUrl : str13;
        String str31 = (i2 & 2097152) != 0 ? serverConfig.customIconName : str14;
        String str32 = (i2 & 4194304) != 0 ? serverConfig.note : str15;
        if ((i2 & 8388608) != 0) {
            z6 = z9;
            str16 = str32;
            j3 = serverConfig.lastPlaybackAccessTime;
        } else {
            j3 = j2;
            str16 = str32;
            z6 = z9;
        }
        return serverConfig.copy(str27, str18, str19, str20, i3, str21, str22, str23, str24, str25, str26, j4, z7, z8, z6, z10, z11, list2, str28, str29, str30, str31, str16, j3);
    }

    @Deprecated(message = "不再使用，保留仅为向后兼容")
    public static /* synthetic */ void getUseEmbyPrefix$annotations() {
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final String getAccessToken() {
        return this.accessToken;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final String getDeviceId() {
        return this.deviceId;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final long getLastLoginTime() {
        return this.lastLoginTime;
    }

    /* JADX INFO: renamed from: component13, reason: from getter */
    public final boolean getIsVerified() {
        return this.isVerified;
    }

    /* JADX INFO: renamed from: component14, reason: from getter */
    public final boolean getDirectOnly() {
        return this.directOnly;
    }

    /* JADX INFO: renamed from: component15, reason: from getter */
    public final boolean getEnableStrmDirectPlay() {
        return this.enableStrmDirectPlay;
    }

    /* JADX INFO: renamed from: component16, reason: from getter */
    public final boolean getUseEmbyPrefix() {
        return this.useEmbyPrefix;
    }

    /* JADX INFO: renamed from: component17, reason: from getter */
    public final boolean getTrustAllCerts() {
        return this.trustAllCerts;
    }

    public final List<BackupRouteConfig> component18() {
        return this.backupRoutes;
    }

    /* JADX INFO: renamed from: component19, reason: from getter */
    public final String getActiveBackupRouteId() {
        return this.activeBackupRouteId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getAlias() {
        return this.alias;
    }

    /* JADX INFO: renamed from: component20, reason: from getter */
    public final String getCustomIconPath() {
        return this.customIconPath;
    }

    /* JADX INFO: renamed from: component21, reason: from getter */
    public final String getCustomIconSourceUrl() {
        return this.customIconSourceUrl;
    }

    /* JADX INFO: renamed from: component22, reason: from getter */
    public final String getCustomIconName() {
        return this.customIconName;
    }

    /* JADX INFO: renamed from: component23, reason: from getter */
    public final String getNote() {
        return this.note;
    }

    /* JADX INFO: renamed from: component24, reason: from getter */
    public final long getLastPlaybackAccessTime() {
        return this.lastPlaybackAccessTime;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getProtocol() {
        return this.protocol;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getHost() {
        return this.host;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getPort() {
        return this.port;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getUsername() {
        return this.username;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final String getPassword() {
        return this.password;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final String getUserId() {
        return this.userId;
    }

    public final ServerConfig copy(String id, String alias, String protocol, String host, int port, String path, String username, String password, String userId, String accessToken, String deviceId, long lastLoginTime, boolean isVerified, boolean directOnly, boolean enableStrmDirectPlay, boolean useEmbyPrefix, boolean trustAllCerts, List<BackupRouteConfig> backupRoutes, String activeBackupRouteId, String customIconPath, String customIconSourceUrl, String customIconName, String note, long lastPlaybackAccessTime) {
        id.getClass();
        alias.getClass();
        protocol.getClass();
        host.getClass();
        username.getClass();
        password.getClass();
        return new ServerConfig(id, alias, protocol, host, port, path, username, password, userId, accessToken, deviceId, lastLoginTime, isVerified, directOnly, enableStrmDirectPlay, useEmbyPrefix, trustAllCerts, backupRoutes, activeBackupRouteId, customIconPath, customIconSourceUrl, customIconName, note, lastPlaybackAccessTime);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ServerConfig)) {
            return false;
        }
        ServerConfig serverConfig = (ServerConfig) other;
        return Intrinsics.areEqual(this.id, serverConfig.id) && Intrinsics.areEqual(this.alias, serverConfig.alias) && Intrinsics.areEqual(this.protocol, serverConfig.protocol) && Intrinsics.areEqual(this.host, serverConfig.host) && this.port == serverConfig.port && Intrinsics.areEqual(this.path, serverConfig.path) && Intrinsics.areEqual(this.username, serverConfig.username) && Intrinsics.areEqual(this.password, serverConfig.password) && Intrinsics.areEqual(this.userId, serverConfig.userId) && Intrinsics.areEqual(this.accessToken, serverConfig.accessToken) && Intrinsics.areEqual(this.deviceId, serverConfig.deviceId) && this.lastLoginTime == serverConfig.lastLoginTime && this.isVerified == serverConfig.isVerified && this.directOnly == serverConfig.directOnly && this.enableStrmDirectPlay == serverConfig.enableStrmDirectPlay && this.useEmbyPrefix == serverConfig.useEmbyPrefix && this.trustAllCerts == serverConfig.trustAllCerts && Intrinsics.areEqual(this.backupRoutes, serverConfig.backupRoutes) && Intrinsics.areEqual(this.activeBackupRouteId, serverConfig.activeBackupRouteId) && Intrinsics.areEqual(this.customIconPath, serverConfig.customIconPath) && Intrinsics.areEqual(this.customIconSourceUrl, serverConfig.customIconSourceUrl) && Intrinsics.areEqual(this.customIconName, serverConfig.customIconName) && Intrinsics.areEqual(this.note, serverConfig.note) && this.lastPlaybackAccessTime == serverConfig.lastPlaybackAccessTime;
    }

    public final String getAccessToken() {
        return this.accessToken;
    }

    public final BackupRouteConfig getActiveBackupRoute() {
        String str = this.activeBackupRouteId;
        Object obj = null;
        if (str == null) {
            return null;
        }
        for (Object obj2 : getBackupRoutesSafe()) {
            if (Intrinsics.areEqual(((BackupRouteConfig) obj2).getId(), str)) {
                obj = obj2;
                break;
            }
        }
        return (BackupRouteConfig) obj;
    }

    public final String getActiveBackupRouteId() {
        return this.activeBackupRouteId;
    }

    public final String getAlias() {
        return this.alias;
    }

    public final List<BackupRouteConfig> getBackupRoutes() {
        return this.backupRoutes;
    }

    public final List<BackupRouteConfig> getBackupRoutesSafe() {
        List<BackupRouteConfig> listEmptyList = this.backupRoutes;
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listEmptyList, 10));
        Iterator<T> it = listEmptyList.iterator();
        while (it.hasNext()) {
            arrayList.add(ServerConfigKt.normalizeBackupRouteConfig((BackupRouteConfig) it.next()));
        }
        return arrayList;
    }

    public final String getCurrentRouteDisplayName() {
        String alias;
        String string;
        BackupRouteConfig activeBackupRoute = getActiveBackupRoute();
        if (activeBackupRoute == null || (alias = activeBackupRoute.getAlias()) == null || (string = StringsKt.trim(alias).toString()) == null) {
            return "主线路";
        }
        if (string.length() <= 0) {
            string = null;
        }
        return string == null ? "主线路" : string;
    }

    public final String getCustomIconName() {
        return this.customIconName;
    }

    public final String getCustomIconPath() {
        return this.customIconPath;
    }

    public final String getCustomIconSourceUrl() {
        return this.customIconSourceUrl;
    }

    public final String getDeviceId() {
        return this.deviceId;
    }

    public final boolean getDirectOnly() {
        return this.directOnly;
    }

    public final String getDisplayAddress() {
        String strNormalizeServerPath = ServerConfigKt.normalizeServerPath(this.path);
        return this.protocol + "://" + this.host + ":" + this.port + strNormalizeServerPath;
    }

    public final ServerConfig getEffectiveServerConfig() {
        List<BackupRouteConfig> backupRoutesSafe = getBackupRoutesSafe();
        String str = this.activeBackupRouteId;
        BackupRouteConfig backupRouteConfig = null;
        Object obj = null;
        if (str != null) {
            for (Object obj2 : backupRoutesSafe) {
                if (Intrinsics.areEqual(((BackupRouteConfig) obj2).getId(), str)) {
                    obj = obj2;
                    break;
                }
            }
            backupRouteConfig = (BackupRouteConfig) obj;
        }
        return backupRouteConfig == null ? copy$default(this, null, null, null, null, 0, null, null, null, null, null, null, 0L, false, false, false, false, false, backupRoutesSafe, null, null, null, null, null, 0L, 16383999, null) : copy$default(this, null, null, backupRouteConfig.getProtocol(), backupRouteConfig.getHost(), backupRouteConfig.getPort(), backupRouteConfig.getPath(), null, null, null, null, null, 0L, false, backupRouteConfig.getDirectOnly(), false, false, false, backupRoutesSafe, backupRouteConfig.getId(), null, null, null, null, 0L, 16375747, null);
    }

    public final boolean getEnableStrmDirectPlay() {
        return this.enableStrmDirectPlay;
    }

    public final String getFullUrl() {
        String strNormalizeServerPath = ServerConfigKt.normalizeServerPath(this.path);
        String str = this.protocol;
        String str2 = this.host;
        int i = this.port;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("://");
        sb.append(str2);
        sb.append(":");
        sb.append(i);
        return f21.w(sb, strNormalizeServerPath, "/");
    }

    public final String getHost() {
        return this.host;
    }

    public final String getId() {
        return this.id;
    }

    public final long getLastLoginTime() {
        return this.lastLoginTime;
    }

    public final long getLastPlaybackAccessTime() {
        return this.lastPlaybackAccessTime;
    }

    public final String getNote() {
        return this.note;
    }

    public final String getPassword() {
        return this.password;
    }

    public final String getPath() {
        return this.path;
    }

    public final int getPort() {
        return this.port;
    }

    public final String getProtocol() {
        return this.protocol;
    }

    public final boolean getTrustAllCerts() {
        return this.trustAllCerts;
    }

    public final boolean getUseEmbyPrefix() {
        return this.useEmbyPrefix;
    }

    public final String getUserId() {
        return this.userId;
    }

    public final String getUsername() {
        return this.username;
    }

    public int hashCode() {
        int i = (f22.i(f22.i(f22.i(this.id.hashCode() * 31, 31, this.alias), 31, this.protocol), 31, this.host) + this.port) * 31;
        String str = this.path;
        int i2 = f22.i(f22.i((i + (str == null ? 0 : str.hashCode())) * 31, 31, this.username), 31, this.password);
        String str2 = this.userId;
        int iHashCode = (i2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.accessToken;
        int iHashCode2 = (iHashCode + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.deviceId;
        int iHashCode3 = str4 == null ? 0 : str4.hashCode();
        long j = this.lastLoginTime;
        int i3 = (((((((((((((iHashCode2 + iHashCode3) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + (this.isVerified ? 1231 : 1237)) * 31) + (this.directOnly ? 1231 : 1237)) * 31) + (this.enableStrmDirectPlay ? 1231 : 1237)) * 31) + (this.useEmbyPrefix ? 1231 : 1237)) * 31) + (this.trustAllCerts ? 1231 : 1237)) * 31;
        List<BackupRouteConfig> list = this.backupRoutes;
        int iHashCode4 = (i3 + (list == null ? 0 : list.hashCode())) * 31;
        String str5 = this.activeBackupRouteId;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.customIconPath;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.customIconSourceUrl;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.customIconName;
        int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.note;
        int iHashCode9 = (iHashCode8 + (str9 != null ? str9.hashCode() : 0)) * 31;
        long j2 = this.lastPlaybackAccessTime;
        return iHashCode9 + ((int) ((j2 >>> 32) ^ j2));
    }

    public final boolean isLoggedIn() {
        return (this.userId == null || this.accessToken == null) ? false : true;
    }

    public final boolean isVerified() {
        return this.isVerified;
    }

    public final void setAccessToken(String str) {
        this.accessToken = str;
    }

    public final void setDeviceId(String str) {
        this.deviceId = str;
    }

    public final void setLastLoginTime(long j) {
        this.lastLoginTime = j;
    }

    public final void setUseEmbyPrefix(boolean z) {
        this.useEmbyPrefix = z;
    }

    public final void setUserId(String str) {
        this.userId = str;
    }

    public final void setVerified(boolean z) {
        this.isVerified = z;
    }

    public String toString() {
        String str = this.id;
        String str2 = this.alias;
        String str3 = this.protocol;
        String str4 = this.host;
        int i = this.port;
        String str5 = this.path;
        String str6 = this.username;
        String str7 = this.password;
        String str8 = this.userId;
        String str9 = this.accessToken;
        String str10 = this.deviceId;
        long j = this.lastLoginTime;
        boolean z = this.isVerified;
        boolean z2 = this.directOnly;
        boolean z3 = this.enableStrmDirectPlay;
        boolean z4 = this.useEmbyPrefix;
        boolean z5 = this.trustAllCerts;
        List<BackupRouteConfig> list = this.backupRoutes;
        String str11 = this.activeBackupRouteId;
        String str12 = this.customIconPath;
        String str13 = this.customIconSourceUrl;
        String str14 = this.customIconName;
        String str15 = this.note;
        long j2 = this.lastPlaybackAccessTime;
        StringBuilder sbZ = bc0.z("ServerConfig(id=", str, ", alias=", str2, ", protocol=");
        f22.v(sbZ, str3, ", host=", str4, ", port=");
        sbZ.append(i);
        sbZ.append(", path=");
        sbZ.append(str5);
        sbZ.append(", username=");
        f22.v(sbZ, str6, ", password=", str7, ", userId=");
        f22.v(sbZ, str8, ", accessToken=", str9, ", deviceId=");
        sbZ.append(str10);
        sbZ.append(", lastLoginTime=");
        sbZ.append(j);
        sbZ.append(", isVerified=");
        sbZ.append(z);
        sbZ.append(", directOnly=");
        sbZ.append(z2);
        sbZ.append(", enableStrmDirectPlay=");
        sbZ.append(z3);
        sbZ.append(", useEmbyPrefix=");
        sbZ.append(z4);
        sbZ.append(", trustAllCerts=");
        sbZ.append(z5);
        sbZ.append(", backupRoutes=");
        sbZ.append(list);
        f22.v(sbZ, ", activeBackupRouteId=", str11, ", customIconPath=", str12);
        f22.v(sbZ, ", customIconSourceUrl=", str13, ", customIconName=", str14);
        sbZ.append(", note=");
        sbZ.append(str15);
        sbZ.append(", lastPlaybackAccessTime=");
        return o71.i(j2, ")", sbZ);
    }

    public ServerConfig(String str, String str2, String str3, String str4, int i, String str5, String str6, String str7, String str8, String str9, String str10, long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, List<BackupRouteConfig> list, String str11, String str12, String str13, String str14, String str15, long j2) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        str4.getClass();
        str6.getClass();
        str7.getClass();
        this.id = str;
        this.alias = str2;
        this.protocol = str3;
        this.host = str4;
        this.port = i;
        this.path = str5;
        this.username = str6;
        this.password = str7;
        this.userId = str8;
        this.accessToken = str9;
        this.deviceId = str10;
        this.lastLoginTime = j;
        this.isVerified = z;
        this.directOnly = z2;
        this.enableStrmDirectPlay = z3;
        this.useEmbyPrefix = z4;
        this.trustAllCerts = z5;
        this.backupRoutes = list;
        this.activeBackupRouteId = str11;
        this.customIconPath = str12;
        this.customIconSourceUrl = str13;
        this.customIconName = str14;
        this.note = str15;
        this.lastPlaybackAccessTime = j2;
    }
}
