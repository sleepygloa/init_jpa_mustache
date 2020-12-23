package com.nambi.book.web.dto.system;

import com.nambi.book.domain.system.Menu;
import lombok.Getter;

@Getter
public class MenuListResponseDto {

    private long menuSeq;
    private long menuParentSeq;
    private long menuLev;
    private String menuCd;
    private String menuNm;
    private String menuIcon;
    private String menuOrder;
    private String deviceFlag;
    private String useYn;

    public MenuListResponseDto(Menu entity) {
        this.menuSeq = entity.getMenuSeq();
        this.menuParentSeq = entity.getMenuParentSeq();
        this.menuLev = entity.getMenuLev();
        this.menuCd = entity.getMenuCd();
        this.menuNm = entity.getMenuNm();
        this.menuIcon = entity.getMenuIcon();
        this.menuOrder = entity.getMenuOrder();
        this.deviceFlag = entity.getDeviceFlag();
        this.useYn = entity.getUseYn();
    }
}
