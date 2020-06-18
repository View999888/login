package com.wkx.bean;

import java.util.List;

//首页的 接口https://edu.zhulong.com/openapi/lesson/getCarouselphoto

public class MainBannerBean {
    public List<CarouselBean> Carousel;
    public List<LiveBean> live;

    public List<CarouselBean> getCarousel() {
        return Carousel;
    }

    public void setCarousel(List<CarouselBean> Carousel) {
        this.Carousel = Carousel;
    }

    public List<LiveBean> getLive() {
        return live;
    }

    public void setLive(List<LiveBean> live) {
        this.live = live;
    }

    public static class CarouselBean {
        /**
         * url : https://a.zhulong.com/poster/newjump/?plan_id=2913&prof=sn&placename_id=64&show_flag=3
         * img : https://f.zhulong.com/poster/202006/05/30/184330e75nhawq3r7xgpcw_300_600_550_310.jpg?t=20190710
         * thumb : https://f.zhulong.com/poster/202006/05/30/184330e75nhawq3r7xgpcw_300_600_550_310.jpg?t=20190710
         */

        private String url;
        private String img;
        private String thumb;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
