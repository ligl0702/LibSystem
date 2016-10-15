package com.lll.library.util;

public interface Constant {

    /**
     * 二维码请求的type
     */
    public static final String REQUEST_SCAN_TYPE = "type";
    /**
     * 普通类型，扫完即关闭
     */
    public static final int REQUEST_SCAN_TYPE_COMMON = 0;
    /**
     * 服务商登记类型，扫描
     */
    public static final int REQUEST_SCAN_TYPE_REGIST = 1;

    /**
     * 扫描类型
     * 条形码或者二维码：REQUEST_SCAN_MODE_ALL_MODE
     * 条形码： REQUEST_SCAN_MODE_BARCODE_MODE
     * 二维码：REQUEST_SCAN_MODE_QRCODE_MODE
     */
    public static final String REQUEST_SCAN_MODE = "ScanMode";
    /**
     * 条形码： REQUEST_SCAN_MODE_BARCODE_MODE
     */
    public static final int REQUEST_SCAN_MODE_BARCODE_MODE = 0X100;
    /**
     * 二维码：REQUEST_SCAN_MODE_ALL_MODE
     */
    public static final int REQUEST_SCAN_MODE_QRCODE_MODE = 0X200;
    /**
     * 条形码或者二维码：REQUEST_SCAN_MODE_ALL_MODE
     */
    public static final int REQUEST_SCAN_MODE_ALL_MODE = 0X300;

    public static final int SCAN_REQUEST_CODE = 103;
    public static final int SCAN_RESULT_CODE = 104;

    public static final String USER_NAME = "userName";
    public static final String USER_PWD = "userPwd";
    public static final String IS_REMEMBER_PWD = "isRememberPwd";
    public static final int REGISTER_EXIST_ERROR_CODE = 202;

    public static final String EXTRA_BOOK_INFO = "bookInfo";

    public static final String EXTRA_QUERY_CONDITION = "queryCondition";

    //request code start
    public static final int REGISTER_REQUEST_CODE = 101;
    public static final int QUERY_REQUEST_CODE = 102;
    //request code end

    public static final String BOOK_TYPE = "bookType";
    public static final String BOOK_TYPE_ID = "bookTypeId";
    public static final String BOOK_TYPE_NAME = "bookTypeName";
    public static final String BOOK_JSON_NAME = "books";
    public static final String TYPE_ID = "typeId";

    public static final int QUERY_LIMIT = 50;

    public static final String SCAN_EXTRA_RESULT = "scanResult";
}
