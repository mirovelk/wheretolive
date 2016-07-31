package xyz.wheretolive.crawl.transport.pid;

public class PIDObject {

    private String __type;
    private String Name;
    private Object NodeName;
    private String Info;
    private double X;
    private double Y;
    private String Ico;
    private int Item;
    private int ListID;
    private Object Type;
    private boolean IsMerged;
    private int Key;

    public int getKey() {
        return Key;
    }

    public void setKey(int key) {
        this.Key = key;
    }

    public boolean isMerged() {
        return IsMerged;
    }

    public void setMerged(boolean isMerged) {
        this.IsMerged = isMerged;
    }

    public Object getType() {
        return Type;
    }

    public void setType(Object type) {
        this.Type = type;
    }

    public int getListID() {
        return ListID;
    }

    public void setListID(int listID) {
        this.ListID = listID;
    }

    public int getItem() {
        return Item;
    }

    public void setItem(int item) {
        this.Item = item;
    }

    public String getIco() {
        return Ico;
    }

    public void setIco(String ico) {
        this.Ico = ico;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        this.Y = y;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        this.X = x;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        this.Info = info;
    }

    public Object getNodeName() {
        return NodeName;
    }

    public void setNodeName(Object nodeName) {
        this.NodeName = nodeName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }
}
