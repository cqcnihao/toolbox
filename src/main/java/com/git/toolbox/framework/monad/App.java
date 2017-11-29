package com.git.toolbox.framework.monad;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created by poan on 2017/11/29.
 */
public class App {


    public static void main(String[] args) {
        PackageDTO packageDTO = new PackageDTO("全家福套餐", 12111, "LK987NBV");
        packageDTO = ParamValidator.of(packageDTO).validate(PackageDTO::getId, Objects::nonNull, "ID为空")
                .validate(PackageDTO::getName, StringUtils::isNoneEmpty, "套餐名为空")
                .validate(PackageDTO::getIdentity, StringUtils::isNoneEmpty, "验证码为空").get();


        System.out.println(packageDTO);

        PackageDTO packageDTO1 = new PackageDTO("", 12111, "");
        packageDTO1 = ParamValidator.of(packageDTO1).validate(PackageDTO::getId, Objects::nonNull, "ID为空")
                .validate(PackageDTO::getName, StringUtils::isNoneEmpty, "套餐名为空")
                .validate(PackageDTO::getIdentity, StringUtils::isNoneEmpty, "验证码为空").get();
        System.out.println(packageDTO1);

    }
}
