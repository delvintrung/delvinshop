package com.example.delvin.config.apiconfig;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống chưa được định nghĩa", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Sai Key Message", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Tên đăng nhập phải có ít nhất 3 ký tự", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Mật khẩu phải có ít nhất 8 ký tự", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Chưa đăng nhập hoặc Token hết hạn", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    GOOGLE_LOGIN_FAIL(1008, "Đăng nhập Google thất bại", HttpStatus.BAD_REQUEST),
    LICENSE_TYPE_EXISTED(2001, "Loại giấy phép đã tồn tại", HttpStatus.BAD_REQUEST),
    LICENSE_TYPE_NOT_FOUND(2002, "Loại giấy phép không tồn tại", HttpStatus.NOT_FOUND),
    LICENSE_PRODUCT_NOT_FOUND(3001, "Sản phẩm giấy phép không tồn tại", HttpStatus.NOT_FOUND),
    GIFTCONTENT_NOT_FOUND(4001, "Nội dung quà tặng không tồn tại", HttpStatus.NOT_FOUND),
    USER_WALLET_NOT_FOUND(5001, "Ví người dùng không tồn tại", HttpStatus.NOT_FOUND),
    INSUFFICIENT_FUNDS(5002, "Số dư trong ví không đủ", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH(6003, "Mật khẩu không khớp", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(6004, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    LICENSE_KEY_NOT_FOUND(7001, "Khóa giấy phép không tồn tại", HttpStatus.NOT_FOUND),
    WALLET_NOT_FOUND(5003, "Ví không tồn tại", HttpStatus.NOT_FOUND),
    PRICE_LICENSE_KEY_NOT_FOUND(8001, "Giá khóa giấy phép không tồn tại", HttpStatus.NOT_FOUND),
    DEPOSIT_NOT_FOUND(9001, "Giao dịch nạp tiền không tồn tại", HttpStatus.NOT_FOUND),
    LICENSE_KEY_NOT_SOLD(7002, "Khóa giấy phép chưa được bán", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
