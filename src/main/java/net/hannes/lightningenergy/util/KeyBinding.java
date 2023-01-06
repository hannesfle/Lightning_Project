package net.hannes.lightningenergy.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_LIGHTNINGENERGY = "key.category.lightningenergy.lightningenergy";
    public static final String KEY_DEBUG = "key.lightningenergy.Debug";

    public static final KeyMapping DEBUG = new KeyMapping(KEY_DEBUG, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_0, KEY_CATEGORY_LIGHTNINGENERGY);
}
