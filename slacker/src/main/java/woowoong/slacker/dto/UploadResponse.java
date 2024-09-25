package woowoong.slacker.dto;

public class UploadResponse {

    private String imageUrl;
    private String message;
    private boolean success;

    // 기본 생성자
    public UploadResponse() {
    }

    // 성공 시 사용하는 생성자
    public UploadResponse(String imageUrl) {
        this.imageUrl = imageUrl;
        this.message = "Upload successful";
        this.success = true;
    }

    // 오류 발생 시 사용하는 생성자
    public UploadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

//    public UploadResponse(String imageUrl) {
//    }

    // Getter and Setter
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

