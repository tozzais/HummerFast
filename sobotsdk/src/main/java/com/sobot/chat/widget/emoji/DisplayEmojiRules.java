package com.sobot.chat.widget.emoji;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 65个 Emoji
 * 参考 https://apps.timwhitlock.info/unicode/inspect?s=%F0%9F%98%83%20%F0%9F%98%84%20%F0%9F%98%81%20%F0%9F%98%86%20%F0%9F%98%85%20%F0%9F%A4%A3%20%F0%9F%98%82%20%F0%9F%99%82%20%F0%9F%98%89%20%F0%9F%98%8A%20%F0%9F%98%87%20%F0%9F%98%8D%20%F0%9F%A4%A9%20%F0%9F%98%98%20%F0%9F%98%9A%20%F0%9F%98%99%20%F0%9F%98%8B%20%F0%9F%98%9C%20%F0%9F%98%9D%20%F0%9F%A4%97%20%F0%9F%A4%AD%20%F0%9F%A4%94%20%F0%9F%A4%90%20%F0%9F%98%91%20%F0%9F%98%8F%20%F0%9F%98%92%20%F0%9F%98%8C%20%F0%9F%98%94%20%F0%9F%98%B7%20%F0%9F%A4%92%20%F0%9F%98%B5%20%F0%9F%A4%A0%20%F0%9F%98%8E%20%F0%9F%A4%93%20%F0%9F%98%B3%20%F0%9F%98%A8%20%F0%9F%98%B0%20%F0%9F%98%A5%20%F0%9F%98%A2%20%F0%9F%98%AD%20%F0%9F%98%B1%20%F0%9F%98%96%20%F0%9F%98%A3%20%F0%9F%98%93%20%F0%9F%98%A0%20%F0%9F%91%8B%20%F0%9F%91%8C%20%E2%9C%8C%20%F0%9F%A4%9F%20%F0%9F%91%8D%20%F0%9F%91%8F%20%F0%9F%A4%9D%20%F0%9F%99%8F%20%F0%9F%92%AA%20%F0%9F%99%87%20%F0%9F%90%AE%20%F0%9F%8C%B9%20%F0%9F%A5%80%20%F0%9F%92%8B%20%E2%9D%A4%EF%B8%8F%20%F0%9F%92%94%20%E2%AD%90%20%F0%9F%8E%89%20%F0%9F%8D%BA%20%F0%9F%8E%81
 */
public enum DisplayEmojiRules {
//    QQBIAOQING0("\uD83D\uDE03", "SMILING FACE WITH OPEN MOUTH"),
//    QQBIAOQING1("\uD83D\uDE04", "SPACE"),
//    QQBIAOQING2("\uD83D\uDE01", "SMILING FACE WITH OPEN MOUTH AND SMILING EYES"),
//    QQBIAOQING3("\uD83D\uDE06", "GRINNING FACE WITH SMILING EYES"),
//    QQBIAOQING4("\uD83D\uDE05", "SMILING FACE WITH OPEN MOUTH AND TIGHTLY-CLOSED EYES"),
//    QQBIAOQING5("\uD83E\uDD23", "SMILING FACE WITH OPEN MOUTH AND COLD SWEAT"),
//    QQBIAOQING6("\uD83D\uDE1D", "ROLLING ON THE FLOOR LAUGHING"),
//    QQBIAOQING7("\uD83D\uDE42", "FACE WITH TEARS OF JOY"),
//    QQBIAOQING8("\uD83D\uDE09", "SLIGHTLY SMILING FACE"),
//
//    QQBIAOQING9("\uD83D\uDE0A", "WINKING FACE"),
//    QQBIAOQING10("\uD83D\uDE07", "SMILING FACE WITH SMILING EYES"),
//    QQBIAOQING11("\uD83D\uDE0D", "SMILING FACE WITH HALO"),
//    QQBIAOQING12("\uD83E\uDD29", "SMILING FACE WITH HEART-SHAPED EYES"),
//    QQBIAOQING13("\uD83D\uDE18", "GRINNING FACE WITH STAR EYES"),
//    QQBIAOQING14("\uD83D\uDE1A", "FACE THROWING A KISS"),
//    QQBIAOQING15("\uD83D\uDE19", "KISSING FACE WITH CLOSED EYES"),
//    QQBIAOQING16("\uD83D\uDE0B", "KISSING FACE WITH SMILING EYES"),
//    QQBIAOQING17("\uD83D\uDE1C", "FACE SAVOURING DELICIOUS FOOD"),
//
//    QQBIAOQING18("\uD83D\uDE1D", "FACE WITH STUCK-OUT TONGUE AND WINKING EYE"),
//    QQBIAOQING19("\uD83E\uDD17", "FACE WITH STUCK-OUT TONGUE AND TIGHTLY-CLOSED EYES"),
//    QQBIAOQING20("\uD83E\uDD2D", "HUGGING FACE"),
//    QQBIAOQING21("\uD83E\uDD14", "SMILING FACE WITH SMILING EYES AND HAND COVERING MOUTH"),
//    QQBIAOQING22("\uD83E\uDD10", "THINKING FACE"),
//    QQBIAOQING23("\uD83D\uDE11", "ZIPPER-MOUTH FACE"),
//    QQBIAOQING24("\uD83D\uDE0F", "EXPRESSIONLESS FACE"),
//    QQBIAOQING25("\uD83D\uDE12", "SMIRKING FACE"),
//    QQBIAOQING26("\uD83D\uDE0C", "UNAMUSED FACE"),
//
//    QQBIAOQING27("\uD83D\uDE14", "RELIEVED FACE"),
//    QQBIAOQING28("\uD83D\uDE37", "PENSIVE FACE"),
//    QQBIAOQING29("\uD83E\uDD12", "FACE WITH MEDICAL MASK"),
//    QQBIAOQING30("\uD83D\uDE35", "FACE WITH THERMOMETER"),
//    QQBIAOQING31("\uD83E\uDD20", "DIZZY FACE"),
//    QQBIAOQING32("\uD83D\uDE0E", "FACE WITH COWBOY HAT"),
//    QQBIAOQING33("\uD83E\uDD13", "SMILING FACE WITH SUNGLASSES"),
//    QQBIAOQING34("\uD83D\uDE33", "NERD FACE"),
//    QQBIAOQING35("\uD83D\uDE28", "FLUSHED FACE"),
//
//    QQBIAOQING36("\uD83D\uDE30", "FACE WITH OPEN MOUTH AND COLD SWEAT"),
//    QQBIAOQING37("\uD83D\uDE25", "DISAPPOINTED BUT RELIEVED FACE"),
//    QQBIAOQING38("\uD83D\uDE22", "CRYING FACE"),
//    QQBIAOQING39("\uD83D\uDE2D", "LOUDLY CRYING FACE"),
//    QQBIAOQING40("\uD83D\uDE31", "FACE SCREAMING IN FEAR"),
//    QQBIAOQING41("\uD83D\uDE16", "CONFOUNDED FACE"),
//    QQBIAOQING42("\uD83D\uDE23", "PERSEVERING FACE"),
//    QQBIAOQING43("\uD83D\uDE13", "FACE WITH COLD SWEAT"),
//    QQBIAOQING44("\uD83D\uDE20", "ANGRY FACE"),
//
//    QQBIAOQING45("\uD83D\uDC4B", "WAVING HAND SIGN"),
//    QQBIAOQING46("\uD83D\uDC4C", "OK HAND SIGN"),
//    QQBIAOQING47("✌", "VICTORY HAND"),
//    QQBIAOQING48("\uD83E\uDD1F", "I LOVE YOU HAND SIGN"),
//    QQBIAOQING49("\uD83D\uDC4D", "THUMBS UP SIGN"),
//    QQBIAOQING50("\uD83D\uDC4F", "CLAPPING HANDS SIGN"),
//    QQBIAOQING51("\uD83E\uDD1D", "HANDSHAKE"),
//    QQBIAOQING52("\uD83D\uDE4F", "PERSON WITH FOLDED HANDS"),
//    QQBIAOQING53("\uD83D\uDCAA", "FLEXED BICEPS"),
//
//    QQBIAOQING54("\uD83D\uDE47", "PERSON BOWING DEEPLY"),
//    QQBIAOQING55("\uD83D\uDC2E", "COW FACE"),
//    QQBIAOQING56("\uD83C\uDF39", "ROSE"),
//    QQBIAOQING57("\uD83E\uDD40", "WILTED FLOWER"),
//    QQBIAOQING58("\uD83D\uDC8B", "KISS MARK"),
//    QQBIAOQING59("❤️", "HEAVY BLACK HEART"),
//    QQBIAOQING60("\uD83D\uDC94", "BROKEN HEART"),
//    QQBIAOQING61("⭐", "WHITE MEDIUM STAR"),
//    QQBIAOQING62("\uD83C\uDF89", "PARTY POPPER"),
//
//    QQBIAOQING63("\uD83C\uDF7A", "BEER MUG"),
//    QQBIAOQING64("\uD83C\uDF81", "WRAPPED PRESENT");

    //去掉低版本上不显示的表情 剩余52个
    QQBIAOQING0("\uD83D\uDE03", "SMILING FACE WITH OPEN MOUTH"),
    QQBIAOQING1("\uD83D\uDE04", "SPACE"),
    QQBIAOQING2("\uD83D\uDE01", "SMILING FACE WITH OPEN MOUTH AND SMILING EYES"),
    QQBIAOQING3("\uD83D\uDE06", "GRINNING FACE WITH SMILING EYES"),
    QQBIAOQING4("\uD83D\uDE05", "SMILING FACE WITH OPEN MOUTH AND TIGHTLY-CLOSED EYES"),
    QQBIAOQING6("\uD83D\uDE1D", "ROLLING ON THE FLOOR LAUGHING"),
    QQBIAOQING8("\uD83D\uDE09", "SLIGHTLY SMILING FACE"),

    QQBIAOQING9("\uD83D\uDE0A", "WINKING FACE"),
    QQBIAOQING10("\uD83D\uDE07", "SMILING FACE WITH SMILING EYES"),
    QQBIAOQING11("\uD83D\uDE0D", "SMILING FACE WITH HALO"),
    QQBIAOQING13("\uD83D\uDE18", "GRINNING FACE WITH STAR EYES"),
    QQBIAOQING14("\uD83D\uDE1A", "FACE THROWING A KISS"),
    QQBIAOQING15("\uD83D\uDE19", "KISSING FACE WITH CLOSED EYES"),
    QQBIAOQING16("\uD83D\uDE0B", "KISSING FACE WITH SMILING EYES"),
    QQBIAOQING17("\uD83D\uDE1C", "FACE SAVOURING DELICIOUS FOOD"),

    QQBIAOQING18("\uD83D\uDE1D", "FACE WITH STUCK-OUT TONGUE AND WINKING EYE"),
    QQBIAOQING23("\uD83D\uDE11", "ZIPPER-MOUTH FACE"),
    QQBIAOQING24("\uD83D\uDE0F", "EXPRESSIONLESS FACE"),
    QQBIAOQING25("\uD83D\uDE12", "SMIRKING FACE"),
    QQBIAOQING26("\uD83D\uDE0C", "UNAMUSED FACE"),

    QQBIAOQING27("\uD83D\uDE14", "RELIEVED FACE"),
    QQBIAOQING28("\uD83D\uDE37", "PENSIVE FACE"),
    QQBIAOQING30("\uD83D\uDE35", "FACE WITH THERMOMETER"),
    QQBIAOQING32("\uD83D\uDE0E", "FACE WITH COWBOY HAT"),
    QQBIAOQING34("\uD83D\uDE33", "NERD FACE"),
    QQBIAOQING35("\uD83D\uDE28", "FLUSHED FACE"),

    QQBIAOQING36("\uD83D\uDE30", "FACE WITH OPEN MOUTH AND COLD SWEAT"),
    QQBIAOQING37("\uD83D\uDE25", "DISAPPOINTED BUT RELIEVED FACE"),
    QQBIAOQING38("\uD83D\uDE22", "CRYING FACE"),
    QQBIAOQING39("\uD83D\uDE2D", "LOUDLY CRYING FACE"),
    QQBIAOQING40("\uD83D\uDE31", "FACE SCREAMING IN FEAR"),
    QQBIAOQING41("\uD83D\uDE16", "CONFOUNDED FACE"),
    QQBIAOQING42("\uD83D\uDE23", "PERSEVERING FACE"),
    QQBIAOQING43("\uD83D\uDE13", "FACE WITH COLD SWEAT"),
    QQBIAOQING44("\uD83D\uDE20", "ANGRY FACE"),

    QQBIAOQING45("\uD83D\uDC4B", "WAVING HAND SIGN"),
    QQBIAOQING46("\uD83D\uDC4C", "OK HAND SIGN"),
    QQBIAOQING47("✌", "VICTORY HAND"),
    QQBIAOQING49("\uD83D\uDC4D", "THUMBS UP SIGN"),
    QQBIAOQING50("\uD83D\uDC4F", "CLAPPING HANDS SIGN"),
    QQBIAOQING52("\uD83D\uDE4F", "PERSON WITH FOLDED HANDS"),
    QQBIAOQING53("\uD83D\uDCAA", "FLEXED BICEPS"),

    QQBIAOQING54("\uD83D\uDE47", "PERSON BOWING DEEPLY"),
    QQBIAOQING55("\uD83D\uDC2E", "COW FACE"),
    QQBIAOQING56("\uD83C\uDF39", "ROSE"),
    QQBIAOQING58("\uD83D\uDC8B", "KISS MARK"),
    QQBIAOQING59("❤️", "HEAVY BLACK HEART"),
    QQBIAOQING60("\uD83D\uDC94", "BROKEN HEART"),
    QQBIAOQING61("⭐", "WHITE MEDIUM STAR"),
    QQBIAOQING62("\uD83C\uDF89", "PARTY POPPER"),

    QQBIAOQING63("\uD83C\uDF7A", "BEER MUG"),
    QQBIAOQING64("\uD83C\uDF81", "WRAPPED PRESENT");

    /*********************************
     * 操作
     **************************************/
    private String emojiCode;
    private String emojiDes;
    private static Map<String, String> sEmojiMap;

    private DisplayEmojiRules(String emojiCode, String emojiDes) {
        this.emojiDes = emojiDes;
        this.emojiCode = emojiCode;
    }

    public String getEmojiDes() {
        return emojiDes;
    }

    public void setEmojiDes(String emojiDes) {
        this.emojiDes = emojiDes;
    }

    public String getEmojiCode() {
        return emojiCode;
    }

    public void setEmojiCode(String emojiCode) {
        this.emojiCode = emojiCode;
    }

    /**
     * 提高效率，忽略线程安全
     */
    public static Map<String, String> getMapAll(Context context) {
        if (sEmojiMap == null) {
            sEmojiMap = new HashMap<>();
            for (DisplayEmojiRules data : values()) {
                sEmojiMap.put(data.getEmojiCode(), data.getEmojiDes());
            }
        }
        return sEmojiMap;
    }

    public static ArrayList<EmojiconNew> getListAll(Context context) {
        ArrayList<EmojiconNew> sEmojiList = new ArrayList<>();
        for (DisplayEmojiRules data : values()) {
            sEmojiList.add(new EmojiconNew(data.getEmojiCode(), data.getEmojiDes()));
        }
        return sEmojiList;
    }
}