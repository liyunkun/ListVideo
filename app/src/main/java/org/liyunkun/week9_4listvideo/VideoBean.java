package org.liyunkun.week9_4listvideo;

/**
 * Created by liyunkun on 2016/10/27 0027.
 */
public class VideoBean {

    private String videoUrl;//视频的地址
    private String imageUrl;//覆盖图的地址
    private String title;//标题
    private int width;//视频的宽
    private int height;//视频的高

    public VideoBean() {
    }

    public VideoBean(String videoUrl, String imageUrl, String title, int width, int height) {
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
