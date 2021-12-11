package com.yourcodereview.dev.yudin.entities;

public class StatsObject implements Comparable<StatsObject>{

    private String link;
    private String original;
    private int rank;
    private int count;

    public StatsObject() {
    }

    public StatsObject(String link, String original, int rank, int count) {
        this.link = link;
        this.original = original;
        this.rank = rank;
        this.count = count;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StatsObject{" +
                "link='" + link + '\'' +
                ", original='" + original + '\'' +
                ", rank=" + rank +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(StatsObject o) {
        if (getCount() > o.getCount()) return 1;
        else if(getCount() < o.getCount()) return -1;
        else return 0;
    }
}
