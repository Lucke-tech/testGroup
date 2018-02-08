package web.listener;

import dao.UserLoginInformation;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

public class ServletStartListener implements ServletContextListener {

    public ServletStartListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //在内存中保存用户名单，以此在某些时候提高查询效率，这是暂时的处理方法，应用更加节约的方法解决
        new UserLoginInformation();
        //TODO 在开始时启用数据库中的事件
        String sql = "set GLOBAL event_scheduler=ON";
        List<String> sqlList = new ArrayList<>();
        sqlList.add(sql);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
