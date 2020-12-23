package com.nambi.book.domain.system;

import com.nambi.book.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    long menuSeq;
    @Column(nullable = false)
    long menuParentSeq;
    @Column(nullable = false)
    long menuLev;
    @Column(nullable = false)
    String menuCd;
    @Column(nullable = false)
    String menuNm;
    @Column(nullable = true)
    String menuIcon;
    @Column(nullable = false)
    String menuOrder;
    @Column(nullable = false)
    String deviceFlag;
    @Column(nullable = false)
    String useYn;

    @Builder
    public Menu(Long menuSeq, Long menuParentSeq, Long menuLev,
                String menuCd, String menuNm, String menuIcon, String menuOrder, String deviceFlag, String useYn
    ){
        this.menuSeq = menuSeq;
        this.menuParentSeq = menuParentSeq;
        this.menuLev = menuLev;
        this.menuCd = menuCd;
        this.menuNm = menuNm;
        this.menuIcon = menuIcon;
        this.menuOrder = menuOrder;
        this.deviceFlag = deviceFlag;
        this.useYn = useYn;
    }


    public void update(String menuCd, String menuNm, String menuIcon, String menuOrder, String deviceFlag, String useYn){
        this.menuCd = menuCd;
        this.menuNm = menuNm;
        this.menuIcon = menuIcon;
        this.menuOrder = menuOrder;
        this.deviceFlag = deviceFlag;
        this.useYn = useYn;
    }
}
