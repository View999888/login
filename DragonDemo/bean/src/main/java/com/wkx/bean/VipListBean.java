package com.wkx.bean;

import java.util.List;

public class VipListBean {
    private List<ListBean> list;
    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {


        private String lesson_id;
        private String lesson_name;
        private String thumb;
        private String studentnum;
        private String teacher_id;
        private String amiba_id;
        private String price;
        private String vip_price;
        private int show_vip_tag;
        private int vip_tag_status;
        private String url;
        private String length;
        private String teacher_name;
        private String comment_rate;
        private String comment_level;
        private String vip_thumb;
        private int  type;

        public int getType() {
            return type;
        }

        public void setType(int pType) {
            type = pType;
        }

        public String getLesson_id() {
            return lesson_id;
        }

        public void setLesson_id(String lesson_id) {
            this.lesson_id = lesson_id;
        }

        public String getLesson_name() {
            return lesson_name;
        }

        public void setLesson_name(String lesson_name) {
            this.lesson_name = lesson_name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getStudentnum() {
            return studentnum;
        }

        public void setStudentnum(String studentnum) {
            this.studentnum = studentnum;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getAmiba_id() {
            return amiba_id;
        }

        public void setAmiba_id(String amiba_id) {
            this.amiba_id = amiba_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVip_price() {
            return vip_price;
        }

        public void setVip_price(String vip_price) {
            this.vip_price = vip_price;
        }

        public int getShow_vip_tag() {
            return show_vip_tag;
        }

        public void setShow_vip_tag(int show_vip_tag) {
            this.show_vip_tag = show_vip_tag;
        }

        public int getVip_tag_status() {
            return vip_tag_status;
        }

        public void setVip_tag_status(int vip_tag_status) {
            this.vip_tag_status = vip_tag_status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getComment_rate() {
            return comment_rate;
        }

        public void setComment_rate(String comment_rate) {
            this.comment_rate = comment_rate;
        }

        public String getComment_level() {
            return comment_level;
        }

        public void setComment_level(String comment_level) {
            this.comment_level = comment_level;
        }

        public String getVip_thumb() {
            return vip_thumb;
        }

        public void setVip_thumb(String vip_thumb) {
            this.vip_thumb = vip_thumb;
        }
    }
}
