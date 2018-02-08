package service;

import dao.DatabaseUntil;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class UploadManager {

    public static  List<Map<String, Object>> uploadUserHead(FileItem item, String filename, String savePath, String userName) throws IOException {
        InputStream in = item.getInputStream();
        String saveFilename = makeFileName(filename);
        String realSavePath = makePath(saveFilename, savePath);
        FileOutputStream out = new FileOutputStream(savePath + realSavePath + File.separator + saveFilename);
        byte buffer[] = new byte[1024];
        int len = 0;
        while((len=in.read(buffer))>0){
            out.write(buffer, 0, len);
        }

        String sql = "UPDATE users SET userHeadPic=? WHERE userName=?";
        List<String> sqlList = new ArrayList<>();
        sqlList.add(sql);
        DatabaseUntil.modifySql(sqlList, new Object[][]{{"img/userHead" + realSavePath + "/" + saveFilename, userName}});

        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userHeadPic", "img/userHead" + realSavePath + "/" + saveFilename);
        data.add(dataMap);


        in.close();
        out.close();

        return data;
    }


    private static String makeFileName(String filename){
        return UUID.randomUUID().toString() + "_" + filename;
    }

    private static String makePath(String filename, String savepath){
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;  //0--15
        int dir2 = (hashcode&0xf0)>>4;  //0-15

        String dir =  "/" + dir1 + "/" + dir2;
        File file = new File(savepath + dir);

        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }
}
