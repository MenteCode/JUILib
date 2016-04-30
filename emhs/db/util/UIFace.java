package emhs.db.util;

import emhs.db.components.UIComponent;

import java.util.HashMap;

public class UIFace {
    private static HashMap<Class<? extends UIComponent>, RenderProcedure> renderProcedures = new HashMap<>();

    public static void addRenderProcedure(Class<? extends UIComponent> classDef, RenderProcedure procedure) {
        renderProcedures.put(classDef, procedure);
    }

    public static RenderProcedure getRenderProcedure(Class<? extends UIComponent> classDef) {
        return renderProcedures.get(classDef);
    }
}
