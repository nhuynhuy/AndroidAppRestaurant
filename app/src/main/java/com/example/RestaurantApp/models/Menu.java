package com.example.RestaurantApp.models;

public class Menu {
    int menuId;
    String menuName;
    byte[] menuPic;

    public Menu(int menuId) {
        this.menuId = menuId;
    }

    public Menu(String menuName) {
        this.menuName = menuName;
    }

    public Menu(int menuId, String menuName, byte[] menuPic) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPic = menuPic;
    }

    public Menu(String menuName, byte[] menuPic) {
        this.menuName = menuName;
        this.menuPic = menuPic;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public byte[] getMenuPic() {
        return menuPic;
    }

    public void setMenuPic(byte[] menuPic) {
        this.menuPic = menuPic;
    }
}

