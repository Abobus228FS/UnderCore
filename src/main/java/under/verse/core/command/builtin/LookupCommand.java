package under.verse.core.command.builtin;

import under.verse.core.command.*;
import under.verse.core.plugin.Plugin;
import under.verse.core.plugin.PluginManager;
import java.lang.reflect.*;

public class LookupCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, String label, String[] args) throws ClassNotFoundException {
        if (args.length == 0) {
            sender.sendMessage("§aИспользование: /lookup <класс>");
            return true;
        }
        StringBuilder sb = new StringBuilder();
        Class<?> clazz;
        boolean b = false;
        clazz = Class.forName(args[0]);
        sb.append("\n");
        StringBuilder interfaces = new StringBuilder();
        for (Class<?> interfaze : clazz.getInterfaces()) {
            if (b) interfaces.append(", ");
            else b = true;
            interfaces.append(interfaze.getSimpleName());
        }
        String clazzModifiers = Modifier.toString(clazz.getModifiers());
        Class<?> superclazz = clazz.getSuperclass();
        String superclassStr = "";
        if (superclazz != null) {
            superclassStr = " §bextends §f" + superclazz.getSimpleName();
        }
            String interfacesStr = "";
            if (!interfaces.toString().equals("") && !clazz.isAnnotation()) {
            interfacesStr = " §bimplements §f" + interfaces;
            }
        sb.append("§b" + clazzModifiers + " class §f" + clazz.getSimpleName() + superclassStr + interfacesStr + " §8{\n\n");
        for (Field field : clazz.getDeclaredFields()) {
            String modifiers = Modifier.toString(field.getModifiers());
            String returnn = field.getType().getSimpleName();
            if (!modifiers.equals("")) modifiers += " ";
            b = false;
            sb.append("§b" + modifiers + "§f" + returnn + " " + field.getName() + "§8;\n");
        }
        sb.append("\n");
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            StringBuilder parameters = new StringBuilder();
            b = false;
            for (Class<?> parameter : constructor.getParameterTypes()) {
                if (b) parameters.append(", ");
                else b = true;
                parameters.append(parameter.getSimpleName());
            }
            String modifiers = Modifier.toString(constructor.getModifiers());
            if (!modifiers.equals("")) modifiers += " ";
            b = false;
            StringBuilder exceptions = new StringBuilder();
            for (Class<?> exception : constructor.getExceptionTypes()) {
                if (b) exceptions.append(", ");
                else b = true;
                exceptions.append(exception.getSimpleName());
            }
            String exceptionsStr = "";
            if (!exceptions.toString().equals("")) {
                exceptionsStr = " §2throws §f" + exceptions;
            }
            sb.append("§b" + modifiers + "§f<init>(" + parameters + ")" + exceptionsStr + "§8;\n");
        }
        sb.append("\n");
        for (Method method : clazz.getDeclaredMethods()) {
            String name = method.getName();
            String returnn = method.getReturnType().getSimpleName();
            StringBuilder parameters = new StringBuilder();
            b = false;
            for (Class<?> parameter : method.getParameterTypes()) {
                if (b) parameters.append(", ");
                else b = true;
                parameters.append(parameter.getSimpleName());
            }
            String modifiers = Modifier.toString(method.getModifiers());
            if (!modifiers.equals("")) modifiers += " ";
            b = false;
            StringBuilder exceptions = new StringBuilder();
            for (Class<?> exception : method.getExceptionTypes()) {
                if (b) exceptions.append(", ");
                else b = true;
                exceptions.append(exception.getSimpleName());
            }
            String exceptionsStr = "";
            if (!exceptions.toString().equals("")) {
            exceptionsStr = " §2throws §f" + exceptions;
            }
            sb.append("§b" + modifiers + "§f" + returnn + " " + name + "(" + parameters + ")" + exceptionsStr + "§8;\n");
        }
        sb.append("}");
        sender.sendMessage(sb.toString());
        return true;
    }
}
