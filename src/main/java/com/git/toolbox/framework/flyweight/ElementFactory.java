package com.git.toolbox.framework.flyweight;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by poan on 2017/12/01.
 */
public class ElementFactory {

    private final Map<ElementEnum, Element> elementMap;

    public ElementFactory() {
        this.elementMap = new EnumMap<>(ElementEnum.class);
    }

    Element createElement(ElementEnum elementEnum) {
        Element element = elementMap.get(elementEnum);
        if (element == null) {
            switch (elementEnum) {
                case Fire:
                    element = new Fire();
                    elementMap.put(elementEnum, element);
                    break;
                case Gold:
                    element = new Gold();
                    elementMap.put(elementEnum, element);
                    break;
                case Soil:
                    element = new Soil();
                    elementMap.put(elementEnum, element);
                    break;
                case Wood:
                    element = new Wood();
                    elementMap.put(elementEnum, element);
                    break;
                case Water:
                    element = new Water();
                    elementMap.put(elementEnum, element);
                    break;
            }
        }
        return element;

    }


}
