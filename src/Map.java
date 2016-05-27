import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Map {
    // 0:空位
    // 1：正确位置
    // 2：箱子位置
    // 8:墙壁位置
    // 4:站立位置
    
    // 3：摆放正确
    // 可行动:0,1,4,5
    // 可站里:4,5
    
    public static int H = 8;
    public static int W = 8;
    private static final int NUM = H * W;
    public static int TARGET = 0;
    private int finished = -1;
    private Queue<Map> mapList = new LinkedList<Map>();
    private Queue<Part> boxList = new LinkedList<Part>();
    public Map fatherMap = null;
    short[][] map = new short[H][W];
    private int hashCode;
    
    class Part {
        short x, y;
        
        public Part(short x, short y) {
            this.x = x;
            this.y = y;
        }
    }
    
    /**
     * 获取子节点地图（进行一次BFS搜索）
     *
     * @return
     */
    public LinkedList<Map> getChildMap() {
        LinkedList<Map> list = new LinkedList<Map>();
        for (Part part : boxList) {
            Map temp;
            temp = move(part, -1, 0);
            if (null != temp)
                list.offer(temp);
            temp = move(part, 1, 0);
            if (null != temp)
                list.offer(temp);
            temp = move(part, 0, -1);
            if (null != temp)
                list.offer(temp);
            temp = move(part, 0, 1);
            if (null != temp)
                list.offer(temp);
        }
        return list;
    }
    
    /**
     * 移动箱子
     *
     * @param box
     * @param x
     * @param y
     * @return
     */
    private Map move(Part box, int x, int y) {
        Map temp = null;
        //如果箱子可以向当前方向移动
        if ((map[box.x + x][box.y + y] == 0 || map[box.x + x][box.y + y] == 1
             || map[box.x + x][box.y + y] == 4 || map[box.x + x][box.y + y] == 5)
            && (map[box.x - x][box.y - y] == 4 || map[box.x - x][box.y - y] == 5)) {
            temp = this.clone();
            // TODO待优化
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (temp.map[i][j] == 4 || temp.map[i][j] == 5) {
                        temp.map[i][j] -= 4;
                    }
                }
            }
            temp.map[box.x][box.y] += 4;
            temp.map[box.x][box.y] -= 2;
            temp.map[box.x + x][box.y + y] += 2;
            //因为箱子已经移动，所以需要重新计算可以站立的位置
            temp.fatherMap = this;
            temp.findStart();
            temp.getCode();
        }
        return temp;
    }
    
    public boolean isDead() {
        boolean flag = false;
        for (Part box : boxList) {
            if (map[box.x][box.y] != 3
                && (map[box.x - 1][box.y] == 8 && map[box.x][box.y - 1] == 8)
                && (map[box.x - 1][box.y] == 8 && map[box.x][box.y + 1] == 8)
                && (map[box.x + 1][box.y] == 8 && map[box.x][box.y - 1] == 8)
                && (map[box.x + 1][box.y] == 8 && map[box.x][box.y + 1] == 8)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 填充可移动位置
     */
    public void findStart() {
        //位置队列
        Queue<Part> manList = new LinkedList<Part>();
        for (short i = 0; i < H; i++) {
            for (short j = 0; j < W; j++) {
                //能站人的地方都加入队列
                if (map[i][j] == 4 || map[i][j] == 5) {
                    manList.offer(new Part(i, j));
                }
                //这里用于同时记录箱子的位置，以后会用到，用于减少遍历次数
                if (map[i][j] == 2 || map[i][j] == 3) {
                    boxList.offer(new Part(i, j));
                }
            }
        }
        //提出没一个站立位置并将其周围填充之后加入队列
        while (!manList.isEmpty()) {
            Part temp = manList.poll();
            fillMovable(manList, (short) (temp.x - 1), temp.y);
            fillMovable(manList, (short) (temp.x + 1), temp.y);
            fillMovable(manList, temp.x, (short) (temp.y - 1));
            fillMovable(manList, temp.x, (short) (temp.y + 1));
        }
    }
    
    private void fillMovable(Queue<Part> manList, short x, short y) {
        if (map[x][y] == 0 || map[x][y] == 1) {
            map[x][y] += 4;
            manList.offer(new Part(x, y));
        }
    }
    
    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        for (short[] temp : map) {
            for (int temp2 : temp) {
                str.append(String.valueOf(temp2));
            }
            str.append("\n");
        }
        return str.toString().replaceAll("8", "#").replaceAll("4", "0").replace("5", "1");
        //		return str.toString().replaceAll("8", "#");
    }
    
    /**
     * 设置目标箱子数量
     */
    public void setTarget() {
        TARGET = 0;
        for (short[] temp : map) {
            for (int temp2 : temp) {
                if (temp2 == 3 || temp2 == 1 || temp2 == 5) {
                    TARGET++;
                }
            }
        }
    }
    
    /**
     * 获取完成数
     * 
     * @return
     */
    public boolean isFinished() {
        if (finished < 0)
            getFinish();
        return finished == TARGET;
    }
    
    private void getFinish() {
        finished = 0;
        for (short[] temp : map) {
            for (int temp2 : temp) {
                if (temp2 == 3) {
                    finished++;
                }
            }
        }
    }
    
    public void getCode() {
        hashCode = 0;
        int i = 0;
        for (short[] temp : map) {
            for (short temp2 : temp) {
                hashCode += ((int) Math.pow(7, NUM - i)) * temp2;
                ++i;
            }
        }
    }
    
    @Override
    public Map clone() {
        Map temp = new Map();
        temp.finished = this.finished;
        temp.map = new short[H][W];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                temp.map[i][j] = this.map[i][j];
            }
        }
        return temp;
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        Map o = (Map) obj;
        if (this.hashCode != o.hashCode)
            return false;
        boolean flag = true;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (o.map[i][j] != this.map[i][j]) {
                    flag = false;
                    break;
                }
            }
            if (!flag)
                break;
        }
        return flag;
    }
}
