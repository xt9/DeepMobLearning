package xt9.deepmoblearning.common.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by xt9 on 2018-01-10.
 */
public class Pagination {
    private int currentPageIndex;
    private int lastPageIndex;
    private int itemsPerpage;

    public Pagination(int currentPageIndex, int listSize, int itemsPerPage) {
        this.currentPageIndex = currentPageIndex;
        this.itemsPerpage = itemsPerPage;
        this.lastPageIndex = getPageSize(listSize);
    }

    public void setCurrentPageIndex(int index) {
        currentPageIndex = index;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getFirstPageIndex() {
        return 0;
    }

    public int getLastPageIndex() {
        return lastPageIndex;
    }

    public void decrease() {
        if((getCurrentPageIndex() - 1) != -1) {
            setCurrentPageIndex(getCurrentPageIndex() - 1);
        }
    }

    public void increase() {
        if((getCurrentPageIndex() + 1) != (getLastPageIndex() + 1)) {
            setCurrentPageIndex(getCurrentPageIndex() + 1);
        }
    }

    public int getPageSize(int listSize) {
        int pageSize = listSize / itemsPerpage;
        int r = listSize % itemsPerpage;
        pageSize = r > 0 ? pageSize + 1 : pageSize;

        return pageSize > 0 ? pageSize - 1 : 0;
    }

    public void update(int currentPage, int newSize) {
        currentPageIndex = currentPage;
        lastPageIndex = getPageSize(newSize);
    }

    public NBTBase serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("currentPageIndex", currentPageIndex);
        nbt.setInteger("lastPageIndex", lastPageIndex);
        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt) {
        currentPageIndex = nbt.getInteger("currentPageIndex");
        lastPageIndex = nbt.getInteger("lastPageIndex");

    }
}
