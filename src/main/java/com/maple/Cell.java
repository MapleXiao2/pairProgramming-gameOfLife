package com.maple;

import lombok.Data;

@Data
public class Cell {
    //留个漏洞 可以不要这两个属性
    private int x;
    private int y;
    private boolean isLive;
    private int livingNum;

    public Cell(boolean isLive, int livingNum) {
        this.isLive = isLive;
        this.livingNum = livingNum;
    }

    public Cell(int x, int y, boolean isLive, int livingNum) {
        this.x = x;
        this.y = y;
        this.isLive = isLive;
        this.livingNum = livingNum;
    }

    public Cell() {
        this.isLive = false;
        this.livingNum = 0;
    }

    public void update() {
        if (this.livingNum == 3) {
            this.isLive = true;
        } else if (this.livingNum == 2) {

        }else{
            this.isLive=false;
        }
    }
}
