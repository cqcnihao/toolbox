package com.git.toolbox.util;

/**
 * Created by poan on 2017/11/23.
 * <p>
 * 用户构建器，实际上是包装模式，
 * 使用连缀的方式设置用户的属性
 */
public class UserBuilder {


    private User user;

    public static UserBuilder BUILDER() {
        User user = new User();
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.user = user;
        return userBuilder;
    }

    public UserBuilder setAge(int age) {
        this.user.setAge(age);
        return this;
    }

    public UserBuilder setName(String name) {
        this.user.setName(name);
        return this;
    }

    public UserBuilder setAdd(String add) {
        this.user.setAdd(add);
        return this;
    }


    public User build() {
        return this.user;
    }

    public static void main(String[] args) {
        User user = UserBuilder.BUILDER().setAdd("朝阳").setAge(11).setName("张三").build();
        System.out.println(user);
    }

}


class User {

    private String name;

    private Integer age;

    private String add;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", add='" + add + '\'' +
                '}';
    }
}