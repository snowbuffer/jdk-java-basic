package com.study.book.command.example7;

public class Client {
    public static void main(String[] args) {
        //先要启动后台，让整个程序运行起来
        CookManager.runCookManager();

        //为了简单，直接用循环模拟多个桌号点菜
        for (int i = 0; i < 5; i++) {
            //创建服务员
            Waiter waiter = new Waiter();
            //创建命令对象，就是要点的菜
            Command chop = new ChopCommand(i);
            Command duck = new DuckCommand(i);
            //点菜，就是把这些菜让服务员记录下来
            waiter.orderDish(chop);
            waiter.orderDish(duck);
            //点菜完毕
            waiter.orderOver();
        }
    }
}

/*
张三厨师正在为0号桌做：绿豆排骨煲
张三厨师为0号桌做好了：绿豆排骨煲,共计耗时=13秒
王五厨师正在为0号桌做：北京烤鸭
李四厨师正在为1号桌做：绿豆排骨煲
李四厨师为1号桌做好了：绿豆排骨煲,共计耗时=5秒
王五厨师为0号桌做好了：北京烤鸭,共计耗时=18秒
张三厨师正在为1号桌做：北京烤鸭
张三厨师为1号桌做好了：北京烤鸭,共计耗时=1秒
李四厨师正在为2号桌做：绿豆排骨煲
李四厨师为2号桌做好了：绿豆排骨煲,共计耗时=12秒
王五厨师正在为2号桌做：北京烤鸭
王五厨师为2号桌做好了：北京烤鸭,共计耗时=7秒
张三厨师正在为3号桌做：绿豆排骨煲
张三厨师为3号桌做好了：绿豆排骨煲,共计耗时=15秒
李四厨师正在为3号桌做：北京烤鸭
王五厨师正在为4号桌做：绿豆排骨煲
李四厨师为3号桌做好了：北京烤鸭,共计耗时=17秒
王五厨师为4号桌做好了：绿豆排骨煲,共计耗时=16秒
张三厨师正在为4号桌做：北京烤鸭
张三厨师为4号桌做好了：北京烤鸭,共计耗时=0秒
*/