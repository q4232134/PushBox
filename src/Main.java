import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Main {
    static HashMap<Integer, Map> list = new HashMap<Integer, Map>();
    static Stack<Map> stack = new Stack<Map>();
    static HashSet<Map> set = new HashSet<Map>();
    
    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        Map.W = 8;
        Map.H = 7;
        Map m1 = readTxtFile("C:\\Users\\Administrator\\Desktop\\1.txt");
        m1.setTarget();
        m1.findStart();
        LinkedList<Map> list = new LinkedList<Map>();
        list.offer(m1);
        Map map = null;
        while (!list.isEmpty()) {
            map = list.pop();
            if (!set.add(map))
                continue;
            System.out.println(list.size() + "--" + set.size());
            if (map.isDead())
                continue;
            list.addAll(map.getChildMap());
            if (map.isFinished())
                break;
        }
        while (null != map) {
            System.out.println(map);
            map = map.fatherMap;
        }
        long end = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("mm:ss:SSS");
        System.out.println(format.format(end - start));
        
    }
    
    static boolean putMap(Map map) {
        boolean flag = true;
        if (!list.containsKey(map.hashCode())) {
            list.put(map.hashCode(), map);
        } else {
            for (int i = 0;; i++) {
                Map temp = list.get(map.hashCode() + i);
                if (temp == null) {
                    list.put(map.hashCode() + i, map);
                    break;
                } else {
                    if (map.equals(temp)) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag)
            System.out.println(map);
        return flag;
    }
    
    /**
     * 读取文件数据生成新的Map
     *
     * @param filePath
     * @return
     */
    public static Map readTxtFile(String filePath) {
        Map map = new Map();
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i = 0, j = 0;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    char[] temp = lineTxt.toCharArray();
                    j = 0;
                    for (char temp2 : temp) {
                        map.map[i][j] = Short.valueOf((short) (temp2 - 48));
                        j++;
                    }
                    i++;
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        map.getCode();
        return map;
    }
}
