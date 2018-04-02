package com.git.poan.designparttern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// 1. 将女神的状态发布抽象为接口
interface GoddessFollower {
    void post(PostType type);
}

// 2. 将女神的状态类型使用枚举列举处理
enum PostType {
    Image(1, "自拍"),
    Voice(2, "语音"),
    Video(3, "视频"),;

    private final int value;
    public final String content;

    PostType(int value, String content) {
        this.value = value;
        this.content = content;
    }
}

// 3. 女神的跟随者实现状态发布接口，并对`2`中的各种状态做对应处理
class Diaosi implements GoddessFollower {

    @Override
    public void post(PostType type) {
        switch (type) {
            case Image:
                System.out.println("Diaosi评价：好看！");
                break;
            case Video:
                System.out.println("Diaosi评价：好美！赞一个");
                break;
            case Voice:
                System.out.println("Diaosi评价：好听！赞一个");
        }
    }
}


// 3. 女神的跟随者实现状态发布接口，并对`2`中的各种状态做对应处理
class FuShuai implements GoddessFollower {

    @Override
    public void post(PostType type) {
        switch (type) {
            case Image:
                System.out.println("FuShuai评价：小姐姐有空么？");
                break;
            case Video:
                System.out.println("FuShuai评价：小姐姐有空么？");
                break;
            case Voice:
                System.out.println("FuShuai评价：小姐姐有空么？");
        }
    }
}

/**
 * Created by poan on 2018/04/02.
 */
public class PiaoCircle {

    private List<GoddessFollower> goddessesFollowers = new ArrayList<>();

    public void addFollower(GoddessFollower goddessesFollower) {
        goddessesFollowers.add(goddessesFollower);
    }

    public void remove(GoddessFollower goddessesFollower) {
        goddessesFollowers.remove(goddessesFollower);
    }

    public void timePass() {

        PostType[] values = PostType.values();
        Random random = new Random();
        int i = random.nextInt(values.length - 1);
        PostType postType = values[i];

        System.out.println("女神发表了" + postType.content);
        for (GoddessFollower goddessesFollower : goddessesFollowers) {
            goddessesFollower.post(postType);
        }
    }

    public static void main(String[] args) {
        PiaoCircle piaoCircle = new PiaoCircle();
        System.out.println("====================Diaosi关注了女神====================");
        Diaosi diaosi = new Diaosi();
        piaoCircle.addFollower(diaosi);
        piaoCircle.timePass();

        System.out.println("====================FuShuai关注了女神======================");
        piaoCircle.addFollower(new FuShuai());
        piaoCircle.timePass();

        System.out.println("====================Diaosi取消关注了女神====================");
        piaoCircle.remove(diaosi);
        piaoCircle.timePass();


    }

}