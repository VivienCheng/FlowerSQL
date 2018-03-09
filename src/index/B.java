package index;

public interface B {
    public Object get(Comparable key);   //查询
    public void remove(Comparable key);    //移除
    public void insertOrUpdate(Comparable key, Object obj); //插入或者更新，如果已经存在，就更新，否则插入
}