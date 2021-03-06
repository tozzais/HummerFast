package com.sobot.chat.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sobot.chat.R;
import com.sobot.chat.SobotApi;
import com.sobot.chat.SobotUIConfig;
import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.adapter.SobotMsgAdapter;
import com.sobot.chat.api.ResultCallBack;
import com.sobot.chat.api.ZhiChiApi;
import com.sobot.chat.api.apiUtils.GsonUtil;
import com.sobot.chat.api.apiUtils.SobotVerControl;
import com.sobot.chat.api.enumtype.SobotChatAvatarDisplayMode;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.CommonModel;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.api.model.SobotCacheFile;
import com.sobot.chat.api.model.SobotEvaluateModel;
import com.sobot.chat.api.model.SobotLocationModel;
import com.sobot.chat.api.model.SobotMsgCenterModel;
import com.sobot.chat.api.model.SobotMultiDiaRespInfo;
import com.sobot.chat.api.model.SobotQuestionRecommend;
import com.sobot.chat.api.model.SobotUserTicketEvaluate;
import com.sobot.chat.api.model.ZhiChiInitModeBase;
import com.sobot.chat.api.model.ZhiChiMessage;
import com.sobot.chat.api.model.ZhiChiMessageBase;
import com.sobot.chat.api.model.ZhiChiPushMessage;
import com.sobot.chat.api.model.ZhiChiReplyAnswer;
import com.sobot.chat.application.MyApplication;
import com.sobot.chat.camera.util.FileUtil;
import com.sobot.chat.core.channel.Const;
import com.sobot.chat.core.channel.SobotMsgManager;
import com.sobot.chat.core.http.callback.StringResultCallBack;
import com.sobot.chat.viewHolder.ImageMessageHolder;
import com.sobot.chat.widget.dialog.SobotDialogUtils;
import com.sobot.chat.widget.dialog.SobotEvaluateDialog;
import com.sobot.chat.widget.dialog.SobotRobotListDialog;
import com.sobot.chat.widget.dialog.SobotTicketEvaluateDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatUtils {

    public static void showThankDialog(final Activity act, Handler handler, final boolean isFinish) {

//        ToastUtil.showToast(act.getApplicationContext(), act.getResources().getString(
//                ResourceUtils.getIdByName(act, "string", "sobot_thank_dialog_hint")));
        CustomToast.makeText(act.getApplicationContext(),
                ResourceUtils.getResString(act, "sobot_thank_dialog_hint"), 1000,
                ResourceUtils.getDrawableId(act.getApplicationContext(), "sobot_iv_login_right")).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!act.isFinishing()) {
                    if (isFinish) {
                        act.finish();
                    }
                }
            }
        }, 2000);
    }

    /**
     * activity????????????????????????
     *
     * @param act
     */
    public static void openSelectPic(Activity act) {
        openSelectPic(act, null);
    }

    /**
     * Fragment????????????????????????
     *
     * @param act
     */
    public static void openSelectPic(Activity act, Fragment childFragment) {
        if (act == null) {
            return;
        }
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        try {
            if (childFragment != null) {
                childFragment.startActivityForResult(intent, ZhiChiConstant.REQUEST_CODE_picture);
            } else {
                act.startActivityForResult(intent, ZhiChiConstant.REQUEST_CODE_picture);
            }
        } catch (Exception e) {
            ToastUtil.showToast(act.getApplicationContext(), ResourceUtils.getResString(act, "sobot_not_open_album"));
        }
    }

    /**
     * Fragment????????????????????????
     *
     * @param act
     */
    public static void openSelectVedio(Activity act, Fragment childFragment) {
        if (act == null) {
            return;
        }
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        }
        try {
            if (childFragment != null) {
                childFragment.startActivityForResult(intent, ZhiChiConstant.REQUEST_CODE_picture);
            } else {
                act.startActivityForResult(intent, ZhiChiConstant.REQUEST_CODE_picture);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast(act.getApplicationContext(), ResourceUtils.getResString(act, "sobot_not_open_album"));
        }
    }


    /**
     * activity????????????
     *
     * @param act
     * @return
     */
    public static File openCamera(Activity act) {
        return openCamera(act, null);
    }

    /**
     * Fragment????????????
     *
     * @param act
     * @param childFragment ???????????????fragment
     * @return
     */
    public static File openCamera(Activity act, Fragment childFragment) {
        String path = SobotPathManager.getInstance().getPicDir() + System.currentTimeMillis() + ".jpg";
        // ?????????????????????????????????
        File cameraFile = new File(path);
        IOUtils.createFolder(cameraFile.getParentFile());
        Uri uri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q || Environment.isExternalStorageLegacy()) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, cameraFile.getAbsolutePath());
                uri = act.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues);
            } else {
                uri = FileOpenHelper.getUri(act, cameraFile);
            }

        } else {
            uri = Uri.fromFile(cameraFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore
                .EXTRA_OUTPUT, uri);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (childFragment != null) {
            childFragment.startActivityForResult(intent, ZhiChiConstant.REQUEST_CODE_makePictureFromCamera);
        } else {
            act.startActivityForResult(intent, ZhiChiConstant.REQUEST_CODE_makePictureFromCamera);
        }

        return cameraFile;
    }

    public static int getResId(Context context, String name) {
        return ResourceUtils.getIdByName(context, "id", name);
    }

    public static int getResDrawableId(Context context, String name) {
        return ResourceUtils.getIdByName(context, "drawable", name);
    }

    public static int getResLayoutId(Context context, String name) {
        return ResourceUtils.getIdByName(context, "layout", name);
    }

    public static int getResStringId(Context context, String name) {
        return ResourceUtils.getIdByName(context, "string", name);
    }

    public static String getResString(Context context, String name) {
        return ResourceUtils.getResString(context, name);
//        return context.getResources().getString(ChatUtils.getResStringId(context, name));
    }

    public static void sendPicByUri(Context context, Handler handler,
                                    Uri selectedImage, ZhiChiInitModeBase initModel, final ListView lv_message,
                                    final SobotMsgAdapter messageAdapter, boolean isCamera) {
        if (initModel == null) {
            return;
        }
        String picturePath = ImageUtils.getPath(context, selectedImage);
        LogUtils.i("picturePath:" + picturePath);
        if (!TextUtils.isEmpty(picturePath)) {
            File tmpFile = new File(picturePath);
            if (tmpFile.exists() && tmpFile.isFile()) {
                sendPicLimitBySize(picturePath, initModel.getCid(),
                        initModel.getPartnerid(), handler, context, lv_message, messageAdapter, isCamera);
            }
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_not_find_pic"));
                return;
            }
            sendPicLimitBySize(file.getAbsolutePath(),
                    initModel.getCid(), initModel.getPartnerid(), handler, context, lv_message, messageAdapter, isCamera);
        }
    }

    public static void sendPicLimitBySize(String filePath, String cid, String uid,
                                          Handler handler, Context context, final ListView lv_message,
                                          final SobotMsgAdapter messageAdapter, boolean isCamera) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q || Environment.isExternalStorageLegacy()) {
            Bitmap bitmap = SobotBitmapUtil.compress(filePath, context, isCamera);
            if (bitmap != null) {
                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                try {
                    int degree = ImageUtils.readPictureDegree(filePath);
                    if (degree > 0) {
                        bitmap = ImageUtils.rotateBitmap(bitmap, degree);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!(filePath.endsWith(".gif") || filePath.endsWith(".GIF"))) {
                    String picDir = SobotPathManager.getInstance().getPicDir();
                    IOUtils.createFolder(picDir);
                    String fName = MD5Util.encode(filePath);
                    filePath = picDir + fName + "_tmp.jpg";
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(filePath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    try {
                        String fName = MD5Util.encode(filePath);
                        Uri uri = ImageUtils.getImageContentUri(context, filePath);
                        filePath = FileUtil.saveImageFile(context, uri, fName + FileUtil.getFileEndWith(filePath), filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                long size = CommonUtils.getFileSize(filePath);
                if (size < (20 * 1024 * 1024)) {
                    String id = System.currentTimeMillis() + "";
                    sendImageMessageToHandler(filePath, handler, id);
                    sendPicture(context, cid, uid, filePath, handler, id, lv_message,
                            messageAdapter);
                } else {
                    ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_file_lt_8M"));
                }
            } else {
                ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_pic_type_error"));
            }
        } else {
            if (!TextUtils.isEmpty(filePath)) {
                long size = CommonUtils.getFileSize(filePath);
                if (size < (20 * 1024 * 1024)) {
                    String id = System.currentTimeMillis() + "";
                    sendImageMessageToHandler(filePath, handler, id);
                    sendPicture(context, cid, uid, filePath, handler, id, lv_message,
                            messageAdapter);
                } else {
                    ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_file_lt_8M"));
                }
            } else {
                ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_pic_type_error"));
            }
        }
    }


    // ????????????
    public static void sendImageMessageToHandler(String imageUrl,
                                                 final Handler handler, String id) {
        ZhiChiMessageBase zhichiMessage = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setMsg(imageUrl);
        zhichiMessage.setAnswer(reply);
        zhichiMessage.setId(id);
        zhichiMessage.setT(Calendar.getInstance().getTime().getTime() + "");
        zhichiMessage.setSendSuccessState(ZhiChiConstant.MSG_SEND_STATUS_LOADING);
        zhichiMessage.setSenderType(ZhiChiConstant.message_sender_type_customer_sendImage + "");
        Message message = new Message();
        message.what = ZhiChiConstant.hander_send_msg;
        message.obj = zhichiMessage;
        handler.sendMessage(message);
    }

    public static void sendPicture(Context context, String cid, String uid,
                                   final String filePath, final Handler handler, final String id,
                                   final ListView lv_message, final SobotMsgAdapter messageAdapter) {
        SobotMsgManager.getInstance(context).getZhiChiApi().sendFile(cid, uid, filePath, "", new ResultCallBack<ZhiChiMessage>() {
            @Override
            public void onSuccess(ZhiChiMessage zhiChiMessage) {
                if (ZhiChiConstant.result_success_code == Integer
                        .parseInt(zhiChiMessage.getCode())) {
                    if (id != null) {
                        Message message = handler.obtainMessage();
                        message.what = ZhiChiConstant.hander_sendPicStatus_success;
                        message.obj = id;
                        handler.sendMessage(message);
                    }
                }
            }

            @Override
            public void onLoading(long total, long current,
                                  boolean isUploading) {
                LogUtils.i("???????????? ??????:" + current);
                if (id != null) {
                    int position = messageAdapter.getMsgInfoPosition(id);
                    LogUtils.i("???????????? position:" + position);
                    updateProgressPartly((int) current, position, lv_message);
                }
            }

            @Override
            public void onFailure(Exception e, String des) {
                LogUtils.i("????????????error:" + des + "exception:" + e);
                if (id != null) {
                    Message message = handler.obtainMessage();
                    message.what = ZhiChiConstant.hander_sendPicStatus_fail;
                    message.obj = id;
                    handler.sendMessage(message);
                }
            }
        });
    }

    /**
     * ????????????????????????   ???????????????????????????progress???
     *
     * @param progress   ????????????
     * @param position   ??????
     * @param lv_message Listview
     */
    public static void updateProgressPartly(int progress, int position, ListView lv_message) {
        int firstVisiblePosition = lv_message.getFirstVisiblePosition();
        int lastVisiblePosition = lv_message.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View view = lv_message.getChildAt(position - firstVisiblePosition);
            if (view.getTag() instanceof ImageMessageHolder) {
                ImageMessageHolder vh = (ImageMessageHolder) view.getTag();
                vh.sobot_pic_progress_round.setProgress(progress);
            }
        }
    }

    public static String getMessageContentByOutLineType(Context context, ZhiChiInitModeBase
            initModel, int type) {
        Resources resources = context.getResources();
        if (1 == type) {// ???????????????
            return initModel.isServiceEndPushFlag() && !TextUtils.isEmpty(initModel.getServiceEndPushMsg()) ? initModel.getServiceEndPushMsg() : "";//ResourceUtils.getResString(context,"sobot_outline_leverByManager");
        } else if (2 == type) { // ??????????????????????????????
            return initModel.isServiceEndPushFlag() && !TextUtils.isEmpty(initModel.getServiceEndPushMsg()) ? initModel.getServiceEndPushMsg() : "";
        } else if (3 == type) { // ??????????????????
            return ResourceUtils.getResString(context, "sobot_outline_leverByManager");
        } else if (4 == type) { // ????????????
            String userOutWord = SharedPreferencesUtil.getStringData(context, ZhiChiConstant.SOBOT_USER_OUT_WORD, "");
            if (!TextUtils.isEmpty(userOutWord)) {
                return userOutWord;
            } else {
                return initModel != null ? initModel.getUserOutWord() : ResourceUtils.getResString(context, "sobot_outline_leverByManager");
            }

        } else if (5 == type) {
            return ResourceUtils.getResString(context, "sobot_outline_leverByManager");
        } else if (6 == type) {
            return ResourceUtils.getResString(context, "sobot_outline_openNewWindows");
        } else if (99 == type) {
            return context.getString(R.string.sobot_outline_leavemsg);
        }
        return null;
    }

    public static ZhiChiMessageBase getUnreadMode(Context context) {
        ZhiChiMessageBase msgBase = new ZhiChiMessageBase();
        msgBase.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");
        ZhiChiReplyAnswer answer = new ZhiChiReplyAnswer();
        answer.setMsg(ResourceUtils.getResString(context, "sobot_no_read"));
        answer.setRemindType(ZhiChiConstant.sobot_remind_type_below_unread);
        msgBase.setAnswer(answer);
        return msgBase;
    }

    /**
     * ?????????????????????mode
     *
     * @param pushMessage ???????????????
     * @return
     */
    public static ZhiChiMessageBase getCustomEvaluateMode(ZhiChiPushMessage pushMessage) {
        ZhiChiMessageBase base = new ZhiChiMessageBase();
        base.setT(Calendar.getInstance().getTime().getTime() + "");
        base.setSenderName(TextUtils.isEmpty(pushMessage.getAname()) ? ResourceUtils.getResString(MyApplication.getInstance(), "sobot_cus_service") : pushMessage.getAname());
        SobotEvaluateModel sobotEvaluateModel = new SobotEvaluateModel();
        sobotEvaluateModel.setIsQuestionFlag(pushMessage.getIsQuestionFlag());
        sobotEvaluateModel.setIsResolved(pushMessage.getIsQuestionFlag() ? 0 : -1);
        base.setSobotEvaluateModel(sobotEvaluateModel);
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        base.setSenderType(ZhiChiConstant.message_sender_type_custom_evaluate + "");
        base.setAction(ZhiChiConstant.action_custom_evaluate);
        base.setAnswer(reply);
        return base;
    }

    public static ZhiChiMessageBase getUploadFileModel(Context context, String tmpMsgId, File selectedFile) {
        ZhiChiMessageBase zhichiMessage = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        SobotCacheFile cacheFile = new SobotCacheFile();
        cacheFile.setMsgId(tmpMsgId);
        cacheFile.setFilePath(selectedFile.getAbsolutePath());
        cacheFile.setFileName(selectedFile.getName());
        cacheFile.setFileType(ChatUtils.getFileType(selectedFile));
        cacheFile.setFileSize(Formatter.formatFileSize(context, selectedFile.length()));
        reply.setCacheFile(cacheFile);
        zhichiMessage.setAnswer(reply);
        zhichiMessage.setId(tmpMsgId);
        zhichiMessage.setT(Calendar.getInstance().getTime().getTime() + "");
        reply.setMsgType(ZhiChiConstant.message_type_file);
        zhichiMessage.setSenderType(ZhiChiConstant.message_sender_type_customer + "");
        return zhichiMessage;
    }

    public static ZhiChiMessageBase getLocationModel(String tmpMsgId, SobotLocationModel data) {
        ZhiChiMessageBase zhichiMessage = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setLocationData(data);
        zhichiMessage.setAnswer(reply);
        zhichiMessage.setId(tmpMsgId);
        zhichiMessage.setT(Calendar.getInstance().getTime().getTime() + "");
        reply.setMsgType(ZhiChiConstant.message_type_location);
        zhichiMessage.setSenderType(ZhiChiConstant.message_sender_type_customer + "");
        return zhichiMessage;
    }

    public static ZhiChiMessageBase getUploadVideoModel(Context context, String tmpMsgId, File selectedFile, String snapshot) {
        ZhiChiMessageBase zhichiMessage = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        SobotCacheFile cacheFile = new SobotCacheFile();
        cacheFile.setMsgId(tmpMsgId);
        cacheFile.setFilePath(selectedFile.getAbsolutePath());
        cacheFile.setFileName(selectedFile.getName());
        cacheFile.setSnapshot(snapshot);
        cacheFile.setFileType(ChatUtils.getFileType(selectedFile));
        cacheFile.setFileSize(Formatter.formatFileSize(context, selectedFile.length()));
        reply.setCacheFile(cacheFile);
        zhichiMessage.setAnswer(reply);
        zhichiMessage.setId(tmpMsgId);
        zhichiMessage.setT(Calendar.getInstance().getTime().getTime() + "");
        reply.setMsgType(ZhiChiConstant.message_type_video);
        zhichiMessage.setSenderType(ZhiChiConstant.message_sender_type_customer + "");
        return zhichiMessage;
    }


    /**
     * ????????? ???????????? ??????????????????????????????
     *
     * @return
     */
    public static ZhiChiMessageBase getRobotTransferTip(Context context, ZhiChiInitModeBase initModel) {
        ZhiChiMessageBase robot = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setMsg(ResourceUtils.getResString(context, "sobot_robot_auto_transfer_tip"));
        reply.setMsgType(ZhiChiConstant.message_type_text + "");
        robot.setT(Calendar.getInstance().getTime().getTime() + "");
        robot.setAnswer(reply);
        robot.setSenderFace(initModel.getRobotLogo());
        robot.setSender(initModel.getRobotName());
        robot.setSenderType(ZhiChiConstant.message_sender_type_robot + "");
        robot.setSenderName(initModel.getRobotName());
        return robot;
    }

    /**
     * ?????????????????????model
     *
     * @param context
     * @param initModel
     * @return
     */
    public static ZhiChiMessageBase getNoticeModel(Context context, ZhiChiInitModeBase initModel) {
        ZhiChiMessageBase robot = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        String announceMsg = initModel.getAnnounceMsg();
        if (!TextUtils.isEmpty(announceMsg)) {
            announceMsg = announceMsg.replace("<p>", "")
                    .replace("</p>", "")
                    .replace("<br/>", "")
                    .replace("\n", "");
        }
        reply.setMsg(announceMsg);
        reply.setMsgType(ZhiChiConstant.message_type_text + "");
        robot.setT(Calendar.getInstance().getTime().getTime() + "");
        robot.setAnswer(reply);
        robot.setSenderType(ZhiChiConstant.message_sender_type_notice + "");
        return robot;
    }

    /**
     * ?????????????????????
     *
     * @param context
     * @param info
     */
    public static void saveOptionSet(Context context, Information info) {
        SharedPreferencesUtil.saveIntData(context, "robot_current_themeImg", info.getTitleImgId());
        SharedPreferencesUtil.saveStringData(context, "sobot_current_sender_face", TextUtils.isEmpty
                (info.getFace()) ? "" : info.getFace());
        SharedPreferencesUtil.saveStringData(context, "sobot_current_sender_name", TextUtils.isEmpty
                (info.getUser_nick()) ? "" : info.getUser_nick());
        SharedPreferencesUtil.saveStringData(context, "sobot_user_phone", TextUtils.isEmpty
                (info.getUser_tels()) ? "" : info.getUser_tels());
        SharedPreferencesUtil.saveStringData(context, "sobot_user_email", TextUtils.isEmpty
                (info.getUser_emails()) ? "" : info.getUser_emails());

        if (TextUtils.isEmpty(info.getPartnerid())) {
            info.setEquipmentId(CommonUtils.getPartnerId(context));
        }
    }


    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param context
     * @param info
     * @return
     */
    public static boolean checkConfigChange(Context context, String appkey, final Information info) {
        if (!SobotVerControl.isPlatformVer) {
            String last_current_appkey = SharedPreferencesUtil.getStringData(context, ZhiChiConstant.sobot_last_current_appkey, "");
            if (!last_current_appkey.equals(info.getApp_key())) {
                SharedPreferencesUtil.removeKey(context, ZhiChiConstant.sobot_last_login_group_id);
                SobotApi.exitSobotChat(context);
                return true;
            }
        }
        String last_current_partnerId = SharedPreferencesUtil.getStringData
                (context, appkey + "_" + ZhiChiConstant.sobot_last_current_partnerId, "");
        String last_current_dreceptionistId = SharedPreferencesUtil.getStringData(
                context, appkey + "_" + ZhiChiConstant.SOBOT_RECEPTIONISTID, "");
        String last_current_robot_code = SharedPreferencesUtil.getStringData(
                context, appkey + "_" + ZhiChiConstant.SOBOT_ROBOT_CODE, "");
        String last_current_remark = SharedPreferencesUtil.getStringData
                (context, appkey + "_" + ZhiChiConstant.sobot_last_current_remark, "");
        String last_current_groupid = SharedPreferencesUtil.getStringData(
                context, appkey + "_" + ZhiChiConstant.sobot_last_current_groupid, "");
        int last_current_service_mode = SharedPreferencesUtil.getIntData(
                context, appkey + "_" + ZhiChiConstant.sobot_last_current_service_mode, -1);
        String last_current_customer_fields = SharedPreferencesUtil.getStringData(
                context, appkey + "_" + ZhiChiConstant.sobot_last_current_customer_fields, "");
        String sobot_last_current_isvip = SharedPreferencesUtil.getStringData(
                context, appkey + "_" + ZhiChiConstant.sobot_last_current_isvip, "");
        String sobot_last_current_vip_level = SharedPreferencesUtil.getStringData(
                context, appkey + "_" + ZhiChiConstant.sobot_last_current_vip_level, "");
        String sobot_last_current_user_label = SharedPreferencesUtil.getStringData(
                context, appkey + "_" + ZhiChiConstant.sobot_last_current_user_label, "");
        String sobot_last_current_robot_alias = SharedPreferencesUtil.getStringData(
                context, appkey + "_" + ZhiChiConstant.sobot_last_current_robot_alias, "");

        // appkey?????????????????????id?????????id???????????????????????????????????????????????????????????????????????????KEY?????? ???userRemark,isvip,vip???????????????????????????????????????
        //????????????uid??????????????????????????????
        if (!last_current_partnerId.equals(info.getPartnerid() == null ? "" : info.getPartnerid())) {
            LogUtils.i("uid?????????????????????????????????..............");
            return true;
        } else if (!last_current_dreceptionistId.equals(info.getChoose_adminid() == null ? "" : info.getChoose_adminid())) {
            LogUtils.i("??????????????????????????????????????????????????????..............");
            return true;
        } else if (!last_current_robot_code.equals(info.getRobotCode() == null ? "" : info.getRobotCode())) {
            LogUtils.i("?????????????????????????????????????????????..............");
            return true;
        } else if (!sobot_last_current_robot_alias.equals(info.getRobot_alias() == null ? "" : info.getRobot_alias())) {
            LogUtils.i("???????????????????????????????????????????????????..............");
            return true;
        } else if (!last_current_remark.equals(info.getRemark() == null ? "" : info.getRemark())) {
            LogUtils.i("????????????????????????????????????..............");
            return true;
        } else if (!last_current_groupid.equals(info.getGroupid() == null ? "" : info.getGroupid())) {
            LogUtils.i("???????????????????????????????????????..............");
            return true;
        } else if (last_current_service_mode != info.getService_mode()) {
            LogUtils.i("??????????????????????????????????????????..............");
            return true;
        } else if (!last_current_customer_fields.equals(info.getCustomer_fields() == null ? "" : info.getCustomer_fields())) {
            LogUtils.i("?????????????????????????????????????????????..............");
            return true;
        } else if (!sobot_last_current_isvip.equals(info.getIsVip() == null ? "" : info.getIsVip())) {
            LogUtils.i("??????vip??????????????????????????????..............");
            return true;
        } else if (!sobot_last_current_vip_level.equals(info.getVip_level() == null ? "" : info.getVip_level())) {
            LogUtils.i("vip????????????????????????????????????..............");
            return true;
        } else if (!sobot_last_current_user_label.equals(info.getUser_label() == null ? "" : info.getUser_label())) {
            LogUtils.i("??????????????????????????????????????????..............");
            return true;
        } else {
            return false;
        }
    }

    /**
     * ?????????????????????
     *
     * @param context
     * @param isSessionOver      ????????????????????????
     * @param isFinish           ?????????????????????
     * @param isExitCommit       ???????????????????????????
     * @param initModel          ???????????????
     * @param current_model      ????????????
     * @param commentType        commentType ???????????? ????????????1 ????????????0
     * @param isBackShowEvaluate ??????????????? ???????????????????????????????????????????????????????????????????????????  true ??? false ???
     * @param isBackShowEvaluate ?????????????????????????????????  true ??? false ???
     */
    public static SobotEvaluateDialog showEvaluateDialog(Activity context, boolean isSessionOver, boolean isFinish, boolean isExitCommit, ZhiChiInitModeBase
            initModel, int current_model, int commentType, String customName, int scroe, int isSolve, String checklables, boolean isBackShowEvaluate, boolean canBackWithNotEvaluation) {
        if (initModel == null) {
            return null;
        }
        SobotEvaluateDialog dialog = null;
        if (ScreenUtils.isFullScreen(context)) {
            dialog = new SobotEvaluateDialog(context, isSessionOver, isFinish, isExitCommit, initModel, current_model, commentType, customName, scroe, isSolve, checklables, isBackShowEvaluate, canBackWithNotEvaluation, ResourceUtils.getIdByName(context, "style", "sobot_FullScreenDialogStyle"));
        } else {
            dialog = new SobotEvaluateDialog(context, isSessionOver, isFinish, isExitCommit, initModel, current_model, commentType, customName, scroe, isSolve, checklables, isBackShowEvaluate, canBackWithNotEvaluation);
        }

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /**
     * ???????????????????????????
     *
     * @param context
     */
    public static SobotTicketEvaluateDialog showTicketEvaluateDialog(Activity context, SobotUserTicketEvaluate evaluate) {

        SobotTicketEvaluateDialog dialog = new SobotTicketEvaluateDialog(context, evaluate);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /**
     * ???????????????????????????
     *
     * @param context
     * @param initMode ???????????????
     */
    public static SobotRobotListDialog showRobotListDialog(Activity context, ZhiChiInitModeBase initMode, SobotRobotListDialog.SobotRobotListListener listener) {
        if (context == null || initMode == null || listener == null) {
            return null;
        }

        SobotRobotListDialog dialog = new SobotRobotListDialog(context, initMode.getPartnerid(), initMode.getRobotid(), listener);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }


    /**
     * ????????????cid???????????????cid
     *
     * @return
     */
    public static String getCurrentCid(ZhiChiInitModeBase initModel, List<String> cids, int currentCidPosition) {
        if (initModel != null) {
            String currentCid = initModel.getCid();
            if (currentCidPosition > 0) {
                if (currentCidPosition > cids.size() - 1) {
                    currentCid = "-1";
                } else {
                    currentCid = cids.get(currentCidPosition);
                }
            }
            return currentCid;
        } else {
            return "-1";
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param context
     * @param ignoreLogic
     * @param title
     * @param companyName
     * @return
     */
    public static String getLogicTitle(Context context, boolean ignoreLogic, String title, String
            companyName) {
        if (ignoreLogic) {
            return title;
        } else {
            int titleDisplayMode = SharedPreferencesUtil.getIntData(context, ZhiChiConstant
                    .SOBOT_CHAT_TITLE_DISPLAY_MODE, SobotChatTitleDisplayMode.Default.getValue());
            if (SobotChatTitleDisplayMode.Default.getValue() == titleDisplayMode) {
                //????????????
                return title;
            } else if (SobotChatTitleDisplayMode.ShowFixedText.getValue() == titleDisplayMode) {
                //??????????????????
                String titleContent = SharedPreferencesUtil.getStringData(context, ZhiChiConstant
                        .SOBOT_CHAT_TITLE_DISPLAY_CONTENT, "");
                if (!TextUtils.isEmpty(titleContent)) {
                    return titleContent;
                } else {
                    //????????????
                    return title;
                }
            } else if (SobotChatTitleDisplayMode.ShowCompanyName.getValue() == titleDisplayMode) {
                //??????????????????
                String titleContent = companyName;
                if (!TextUtils.isEmpty(titleContent)) {
                    return titleContent;
                } else {
                    //????????????
                    return title;
                }
            }
        }
        return title;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param context
     * @param ignoreLogic
     * @param avatar
     * @param companyAvatart
     * @return
     */
    public static String getLogicAvatar(Context context, boolean ignoreLogic, String avatar, String
            companyAvatart) {
        if (ignoreLogic) {
            return avatar;
        } else {
            int titleDisplayMode = SharedPreferencesUtil.getIntData(context, ZhiChiConstant
                    .SOBOT_CHAT_AVATAR_DISPLAY_MODE, SobotChatAvatarDisplayMode.Default.getValue());
            if (SobotChatAvatarDisplayMode.Default.getValue() == titleDisplayMode) {
                //????????????
                return avatar;
            } else if (SobotChatAvatarDisplayMode.ShowFixedAvatar.getValue() == titleDisplayMode) {
                //??????????????????
                String avatarContent = SharedPreferencesUtil.getStringData(context, ZhiChiConstant
                        .SOBOT_CHAT_AVATAR_DISPLAY_CONTENT, "");
                if (!TextUtils.isEmpty(avatarContent)) {
                    return avatarContent;
                } else {
                    //????????????
                    return avatar;
                }
            } else if (SobotChatAvatarDisplayMode.ShowCompanyAvatar.getValue() == titleDisplayMode) {
                //??????????????????
                String titleContent = companyAvatart;
                if (!TextUtils.isEmpty(titleContent)) {
                    return titleContent;
                } else {
                    //????????????
                    return avatar;
                }
            }
        }
        return avatar;
    }

    /**
     * ?????????xx???????????????????????????
     *
     * @param context
     * @param aname
     * @return
     */
    public static ZhiChiMessageBase getServiceAcceptTip(Context context, String aname) {
        ZhiChiMessageBase base = new ZhiChiMessageBase();
        base.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");
        base.setAction(ZhiChiConstant.action_remind_connt_success);
        base.setT(Calendar.getInstance().getTime().getTime() + "");
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setMsgType(null);
        reply.setMsg(ChatUtils.getResString(context, "sobot_service_accept_start") + " " + aname + " " + ChatUtils.getResString(context, "sobot_service_accept_end"));
        reply.setRemindType(ZhiChiConstant.sobot_remind_type_accept_request);
        base.setAnswer(reply);
        return base;
    }

    /**
     * ??????????????????????????????
     *
     * @param aname   ????????????
     * @param aface   ????????????
     * @param content ???????????????
     * @return
     */
    public static ZhiChiMessageBase getServiceHelloTip(String aname, String aface, String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        ZhiChiMessageBase base = new ZhiChiMessageBase();
        base.setT(Calendar.getInstance().getTime().getTime() + "");
        base.setSenderName(TextUtils.isEmpty(aname) ? "" : aname);
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setMsgType(ZhiChiConstant.message_type_text + "");
        base.setSenderType(ZhiChiConstant.message_sender_type_service + "");
        reply.setMsg(content);
        base.setSenderFace(aface);
        base.setAnswer(reply);
        return base;
    }

    /**
     * ??????????????????????????????
     *
     * @param content
     * @return
     */
    public static ZhiChiMessageBase getLeaveMsgTip(String content) {
        ZhiChiMessageBase tmpMsg = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setMsg(content);
        reply.setMsgType(ZhiChiConstant.message_type_text + "");
        tmpMsg.setAnswer(reply);
        tmpMsg.setLeaveMsgFlag(true);
        tmpMsg.setSenderType(ZhiChiConstant.message_sender_type_customer + "");
        tmpMsg.setT(Calendar.getInstance().getTime().getTime() + "");
        return tmpMsg;
    }

    /**
     * @return
     */
    public static ZhiChiMessageBase getInLineHint(String queueDoc) {
        ZhiChiMessageBase paiduizhichiMessageBase = new ZhiChiMessageBase();
        paiduizhichiMessageBase.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");
        paiduizhichiMessageBase.setAction(ZhiChiConstant.action_remind_info_paidui);
        paiduizhichiMessageBase.setT(Calendar.getInstance().getTime().getTime() + "");
        ZhiChiReplyAnswer reply_paidui = new ZhiChiReplyAnswer();
        reply_paidui.setMsg(queueDoc);
        reply_paidui.setRemindType(ZhiChiConstant.sobot_remind_type_paidui_status);
        paiduizhichiMessageBase.setAnswer(reply_paidui);
        return paiduizhichiMessageBase;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param context
     * @param isComment
     * @param current_client_model
     * @return
     */
    public static boolean isEvaluationCompletedExit(Context context, boolean isComment, int current_client_model) {

        boolean evaluationCompletedExit = SharedPreferencesUtil.getBooleanData
                (context, ZhiChiConstant.SOBOT_CHAT_EVALUATION_COMPLETED_EXIT, false);
        if (evaluationCompletedExit && isComment && current_client_model == ZhiChiConstant.client_model_customService) {
            return true;
        }
        return false;
    }

    /**
     * ????????????
     *
     * @param context
     */
    public static void userLogout(final Context context) {

        SharedPreferencesUtil.saveBooleanData(context, ZhiChiConstant.SOBOT_IS_EXIT, true);
        String cid = SharedPreferencesUtil.getStringData(context, Const.SOBOT_CID, "");
        String uid = SharedPreferencesUtil.getStringData(context, Const.SOBOT_UID, "");
        //????????????
        ZCSobotApi.closeIMConnection(context);
        if (!TextUtils.isEmpty(cid) && !TextUtils.isEmpty(uid)) {
            ZhiChiApi zhiChiApi = SobotMsgManager.getInstance(context).getZhiChiApi();
            zhiChiApi.out(cid, uid, new StringResultCallBack<CommonModel>() {
                @Override
                public void onSuccess(CommonModel result) {
                }

                @Override
                public void onFailure(Exception e, String des) {

                }
            });
        }
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param manualType ???????????????????????? ?????????1????????????0 ?????????????????????1,1,1,1=?????????????????????????????????????????????????????????????????????????????????
     * @param answerType (1,9,11,12,14)???????????????????????????2.8.3??????????????????????????????????????????????????????
     * @return true??????????????????
     */
    public static boolean checkManualType(String manualType, String answerType) {
        if (TextUtils.isEmpty(manualType) || TextUtils.isEmpty(answerType)) {
            return false;
        }
        try {
            Integer type = Integer.valueOf(answerType);
            String[] mulArr = manualType.split(",");
            if ((type == 1 && "1".equals(mulArr[0])) || (type == 9 && "1".equals(mulArr[0])) || (type == 11 && "1".equals(mulArr[0])) || (type == 12 && "1".equals(mulArr[0])) || (type == 14 && "1".equals(mulArr[0])) || (type == 2 && "1".equals(mulArr[1]))
                    || (type == 4 && "1".equals(mulArr[2])) || (type == 3 && "1".equals(mulArr[3]))) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void sendPicByFilePath(Context context, String filePath, SobotSendFileListener listener, boolean isCamera) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q || Environment.isExternalStorageLegacy()) {
            Bitmap bitmap = SobotBitmapUtil.compress(filePath, context, isCamera);
            if (bitmap != null) {
                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                try {
                    int degree = ImageUtils.readPictureDegree(filePath);
                    if (degree > 0) {
                        bitmap = ImageUtils.rotateBitmap(bitmap, degree);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!(filePath.endsWith(".gif") || filePath.endsWith(".GIF"))) {
                    String picDir = SobotPathManager.getInstance().getPicDir();
                    IOUtils.createFolder(picDir);
                    String fName = MD5Util.encode(filePath);
                    filePath = picDir + fName + "_tmp.jpg";
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(filePath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_pic_type_error"));
                        return;
                    }
                } else {
                    try {
                        String fName = MD5Util.encode(filePath);
                        Uri uri = ImageUtils.getImageContentUri(context, filePath);
                        filePath = FileUtil.saveImageFile(context, uri, fName + FileUtil.getFileEndWith(filePath), filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_pic_type_error"));
                        return;
                    }
                }
                listener.onSuccess(filePath);
            } else {
                ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_pic_type_error"));
                listener.onError();
            }
        } else {
            if(!TextUtils.isEmpty(filePath)){
                listener.onSuccess(filePath);
            }else{
                ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_pic_type_error"));
                listener.onError();
            }
        }
    }

    public static void sendPicByUriPost(Context context, Uri selectedImage, SobotSendFileListener listener, boolean isCamera) {
        String picturePath = ImageUtils.getPath(context, selectedImage);
        if (!TextUtils.isEmpty(picturePath)) {
            sendPicByFilePath(context, picturePath, listener, isCamera);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                SobotDialogUtils.stopProgressDialog(context);
                ToastUtil.showToast(context, ResourceUtils.getResString(context, "sobot_not_find_pic"));
                return;
            }
            sendPicByFilePath(context, picturePath, listener, isCamera);
        }
    }

    public interface SobotSendFileListener {
        void onSuccess(String filePath);

        void onError();
    }

    /**
     * ??????????????????   ?????????????????????
     *
     * @return
     */
    public static boolean isQuestionFlag(SobotEvaluateModel evaluateModel) {
        if (evaluateModel != null) {
            return evaluateModel.getIsQuestionFlag();
        }
        return false;
    }

    /**
     * ??????????????????
     *
     * @param context
     * @param info
     * @param appkey
     * @param initModel
     * @param messageList
     */
    public static void saveLastMsgInfo(Context context, Information info, String appkey, ZhiChiInitModeBase initModel, List<ZhiChiMessageBase> messageList) {
        SobotCache sobotCache = SobotCache.get(context);

        SobotMsgCenterModel sobotMsgCenterModel = new SobotMsgCenterModel();
        sobotMsgCenterModel.setInfo(info);
        sobotMsgCenterModel.setFace(initModel.getCompanyLogo());
        sobotMsgCenterModel.setName(initModel.getCompanyName());
        sobotMsgCenterModel.setAppkey(appkey);
//		sobotMsgCenterModel.setLastDateTime(Calendar.getInstance().getTime().getTime()+"");
        sobotMsgCenterModel.setUnreadCount(0);

        if (messageList != null) {
            String continueType = ZhiChiConstant.message_sender_type_consult_info + "";
            for (int i = messageList.size() - 1; i >= 0; i--) {
                ZhiChiMessageBase tempMsg = messageList.get(i);
                if (continueType.equals(tempMsg.getSenderType())) {
                    continue;
                }
                sobotMsgCenterModel.setSenderName(tempMsg.getSenderName());
                if (TextUtils.isEmpty(tempMsg.getSenderFace())) {
                    //??????????????????????????????
                    sobotMsgCenterModel.setSenderFace("https://img.sobot.com/console/common/face/user.png");
                } else {
                    sobotMsgCenterModel.setSenderFace("");
                }
                String lastMsg = "";
                if ((ZhiChiConstant.message_sender_type_customer_sendImage + "").equals(tempMsg.getSenderType())) {
                    lastMsg = ResourceUtils.getResString(context, "sobot_upload");
                } else if ((ZhiChiConstant.message_sender_type_send_voice + "").equals(tempMsg.getSenderType())) {
                    lastMsg = ResourceUtils.getResString(context, "sobot_chat_type_voice");
                } else if (tempMsg.getAnswer() != null) {
                    if ((ZhiChiConstant.message_type_pic + "").equals(tempMsg.getAnswer().getMsgType())) {
                        lastMsg = ResourceUtils.getResString(context, "sobot_upload");
                    } else {
                        if (tempMsg.getAnswer().getMsg() == null) {
                            if ((ZhiChiConstant.message_type_card + "").equals(tempMsg.getAnswer().getMsgType())) {
                                lastMsg = ResourceUtils.getResString(context, "sobot_chat_type_goods");
                            } else if ((ZhiChiConstant.message_type_ordercard + "").equals(tempMsg.getAnswer().getMsgType())) {
                                lastMsg = ResourceUtils.getResString(context, "sobot_chat_type_card");
                            } else if ((ZhiChiConstant.message_type_video + "").equals(tempMsg.getAnswer().getMsgType())) {
                                lastMsg = ResourceUtils.getResString(context, "sobot_upload_video");
                            } else if ((ZhiChiConstant.message_type_file + "").equals(tempMsg.getAnswer().getMsgType())) {
                                lastMsg = ResourceUtils.getResString(context, "sobot_choose_file");
                            } else {
                                lastMsg = ResourceUtils.getResString(context, "sobot_chat_type_other_msg");
                            }
                        } else {
                            if (GsonUtil.isMultiRoundSession(tempMsg)) {
                                lastMsg = tempMsg.getAnswer().getMultiDiaRespInfo().getAnswer();
                            } else {
                                if (GsonUtil.isMultiRoundSession(tempMsg)) {
                                    lastMsg = tempMsg.getAnswer().getMultiDiaRespInfo().getAnswer();
                                } else {
                                    lastMsg = tempMsg.getAnswer().getMsg();
                                }
                            }

                        }
                    }
                }
                sobotMsgCenterModel.setLastMsg(lastMsg);
                sobotMsgCenterModel.setLastDate(DateUtil.toDate(Long.parseLong(!TextUtils.isEmpty(tempMsg.getT()) ? tempMsg.getT() : Calendar.getInstance().getTime().getTime() + ""), DateUtil.DATE_FORMAT));
                sobotMsgCenterModel.setLastDateTime(!TextUtils.isEmpty(tempMsg.getT()) ? tempMsg.getT() : Calendar.getInstance().getTime().getTime() + "");
                break;
            }
            sobotCache.put(SobotMsgManager.getMsgCenterDataKey(appkey, info.getPartnerid()), sobotMsgCenterModel);

            ArrayList<String> msgDatas = (ArrayList<String>) sobotCache.getAsObject(SobotMsgManager.getMsgCenterListKey(info.getPartnerid()));
            if (msgDatas == null) {
                msgDatas = new ArrayList<String>();
            }
            if (!msgDatas.contains(appkey)) {
                msgDatas.add(appkey);
                sobotCache.put(SobotMsgManager.getMsgCenterListKey(info.getPartnerid()), msgDatas);
            }
            SharedPreferencesUtil.removeKey(context, ZhiChiConstant.SOBOT_CURRENT_IM_APPID);
            Intent lastMsgIntent = new Intent(ZhiChiConstant.SOBOT_ACTION_UPDATE_LAST_MSG);
            lastMsgIntent.putExtra("lastMsg", sobotMsgCenterModel);
            LocalBroadcastManager.getInstance(context).sendBroadcast(lastMsgIntent);

            SharedPreferencesUtil.saveStringData(context, ZhiChiConstant.SOBOT_LAST_MSG_CONTENT, sobotMsgCenterModel.getLastMsg());
        }

    }

    public static void sendMultiRoundQuestions(Context context, SobotMultiDiaRespInfo multiDiaRespInfo, Map<String, String> interfaceRet, SobotMsgAdapter.SobotMsgCallBack msgCallBack) {
        if (context != null && multiDiaRespInfo != null && interfaceRet != null) {
            ZhiChiMessageBase msgObj = new ZhiChiMessageBase();
            String content = "{\"interfaceRetList\":[" + GsonUtil.map2Json(interfaceRet) + "]," + "\"template\":" + multiDiaRespInfo.getTemplate() + "}";

            msgObj.setContent(formatQuestionStr(multiDiaRespInfo.getOutPutParamList(), interfaceRet, multiDiaRespInfo));
            msgObj.setId(System.currentTimeMillis() + "");
            if (msgCallBack != null) {
                msgCallBack.sendMessageToRobot(msgObj, 4, 2, content, interfaceRet.get("title"));
            }
        }
    }

    private static String formatQuestionStr(String[] outPutParam, Map<String, String> interfaceRet, SobotMultiDiaRespInfo multiDiaRespInfo) {
        if (multiDiaRespInfo != null && interfaceRet != null && interfaceRet.size() > 0) {
            Map<String, String> map = new HashMap<>();
            map.put("level", multiDiaRespInfo.getLevel());
            map.put("conversationId", multiDiaRespInfo.getConversationId());
            if (outPutParam != null && outPutParam.length > 0) {
                for (int i = 0; i < outPutParam.length; i++) {
                    map.put(outPutParam[i], interfaceRet.get(outPutParam[i]));
                }
            }
            return GsonUtil.map2Str(map);
        }
        return "";
    }

    public static String getMultiMsgTitle(SobotMultiDiaRespInfo multiDiaRespInfo) {
        if (multiDiaRespInfo == null) {
            return "";
        }
        if ("000000".equals(multiDiaRespInfo.getRetCode())) {
            if (multiDiaRespInfo.getEndFlag()) {
                return !TextUtils.isEmpty(multiDiaRespInfo.getAnswerStrip()) ? multiDiaRespInfo.getAnswerStrip() : multiDiaRespInfo.getAnswer();
            } else {
                return multiDiaRespInfo.getRemindQuestion();
            }
        }
        return multiDiaRespInfo.getRetErrorMsg();
    }

    /**
     * ???????????????????????????
     *
     * @param initModel ???????????????
     * @return
     */
    public static ZhiChiMessageBase getQuestionRecommendData(final ZhiChiInitModeBase initModel, final SobotQuestionRecommend data) {
        ZhiChiMessageBase robot = new ZhiChiMessageBase();
        robot.setSenderType(ZhiChiConstant.message_sender_type_questionRecommend + "");
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        robot.setT(Calendar.getInstance().getTime().getTime() + "");
        reply.setQuestionRecommend(data);
        robot.setAnswer(reply);
        robot.setSenderFace(initModel.getRobotLogo());
        robot.setSender(initModel.getRobotName());
        robot.setSenderName(initModel.getRobotName());
        return robot;
    }

    public static ZhiChiMessageBase getTipByText(String content) {
        ZhiChiMessageBase data = new ZhiChiMessageBase();
        data.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");
        data.setT(Calendar.getInstance().getTime().getTime() + "");
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setMsg(content);
        reply.setRemindType(ZhiChiConstant.sobot_remind_type_tip);
        data.setAnswer(reply);
        return data;
    }

    public static void msgLogicalProcess(ZhiChiInitModeBase initModel, SobotMsgAdapter messageAdapter, ZhiChiPushMessage pushMessage) {
        if (initModel != null && ChatUtils.isNeedWarning(pushMessage.getContent(), initModel.getAccountStatus())) {
            messageAdapter.justAddData(ChatUtils.getTipByText(ResourceUtils.getResString(MyApplication.getInstance(), "sobot_money_trading_tip")));
        }
    }

    private static boolean isNeedWarning(String content, int accountStatus) {
        return !TextUtils.isEmpty(content) && (accountStatus == ZhiChiConstant.SOBOT_ACCOUNTSTATUS_FREE_EDITION
                || accountStatus == ZhiChiConstant.SOBOT_ACCOUNTSTATUS_TRIAL_EDITION)
                && content.contains(ResourceUtils.getResString(MyApplication.getInstance(), "sobot_ver_code"));
    }

    //?????????????????????????????????-1??????????????????0??????
    public static boolean isFreeAccount(int accountStatus) {
        return accountStatus == ZhiChiConstant.SOBOT_ACCOUNTSTATUS_FREE_EDITION
                || accountStatus == ZhiChiConstant.SOBOT_ACCOUNTSTATUS_INACTIVATED;
    }

    /**
     * ????????????????????????????????????view
     *
     * @param context
     */
    public static TextView initAnswerItemTextView(Context context, boolean isHistoryMsg) {
        TextView answer = new TextView(context);
        answer.setTextSize(14);
        answer.setPadding(0, ScreenUtils.dip2px(context, 7), 0, ScreenUtils.dip2px(context, 7));
        answer.setLineSpacing(2f, 1f);
        // ??????????????????????????????
        int colorId = 0;
        if (isHistoryMsg) {
            colorId = SobotUIConfig.DEFAULT != SobotUIConfig.sobot_chat_left_textColor ? SobotUIConfig.sobot_chat_left_textColor :
                    ResourceUtils.getIdByName(context, "color", "sobot_color_suggestion_history");
        } else {
            colorId = SobotUIConfig.DEFAULT != SobotUIConfig.sobot_chat_left_link_textColor ? SobotUIConfig.sobot_chat_left_link_textColor :
                    ResourceUtils.getIdByName(context, "color", "sobot_color_link");
        }
        answer.setTextColor(context.getResources().getColor(colorId));
        return answer;
    }

    /**
     * ??????????????????????????????icon
     *
     * @param context
     * @param fileType
     * @return
     */
    public static int getFileIcon(Context context, int fileType) {
        int tmpResId;
        if (context == null) {
            return 0;
        }
        switch (fileType) {
            case ZhiChiConstant.MSGTYPE_FILE_DOC:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_doc");
                break;
            case ZhiChiConstant.MSGTYPE_FILE_PPT:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_ppt");
                break;
            case ZhiChiConstant.MSGTYPE_FILE_XLS:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_xls");
                break;
            case ZhiChiConstant.MSGTYPE_FILE_PDF:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_pdf");
                break;
            case ZhiChiConstant.MSGTYPE_FILE_MP3:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_mp3");
                break;
            case ZhiChiConstant.MSGTYPE_FILE_MP4:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_mp4");
                break;
            case ZhiChiConstant.MSGTYPE_FILE_RAR:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_rar");
                break;
            case ZhiChiConstant.MSGTYPE_FILE_TXT:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_txt");
                break;
            case ZhiChiConstant.MSGTYPE_FILE_OTHER:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_unknow");
                break;
            default:
                tmpResId = ResourceUtils.getIdByName(context, "drawable", "sobot_icon_file_unknow");
                break;
        }
        return tmpResId;
    }

    /**
     * ?????????????????????????????????
     *
     * @param file
     * @return
     */
    public static int getFileType(File file) {
        int tmpFileType = ZhiChiConstant.MSGTYPE_FILE_OTHER;
        if (file == null) {
            return tmpFileType;
        }
        try {
            String name = file.getName().toLowerCase();
            if (name.endsWith("doc") || name.endsWith("docx")) {
                return ZhiChiConstant.MSGTYPE_FILE_DOC;
            } else if (name.endsWith("ppt") || name.endsWith("pptx")) {
                return ZhiChiConstant.MSGTYPE_FILE_PPT;
            } else if (name.endsWith("xls") || name.endsWith("xlsx")) {
                return ZhiChiConstant.MSGTYPE_FILE_XLS;
            } else if (name.endsWith("pdf")) {
                return ZhiChiConstant.MSGTYPE_FILE_PDF;
            } else if (name.endsWith("mp3")) {
                return ZhiChiConstant.MSGTYPE_FILE_MP3;
            } else if (name.endsWith("mp4")) {
                return ZhiChiConstant.MSGTYPE_FILE_MP4;
            } else if (name.endsWith("rar") || name.endsWith("zip")) {
                return ZhiChiConstant.MSGTYPE_FILE_RAR;
            } else if (name.endsWith("txt")) {
                return ZhiChiConstant.MSGTYPE_FILE_TXT;
            }
        } catch (Exception e) {
            //ignor
        }
        return tmpFileType;
    }
}