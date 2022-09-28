/*
 * Copyright 2017-2018 Baidu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.keven1z.utils;


import com.strobel.assembler.metadata.ArrayTypeLoader;
import com.strobel.assembler.metadata.MetadataSystem;
import com.strobel.assembler.metadata.TypeReference;
import com.strobel.decompiler.DecompilationOptions;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.languages.BytecodeOutputOptions;
import com.strobel.decompiler.languages.java.JavaFormattingOptions;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 反编译工具类
 * @author: anyang
 * @create: 2018/10/18 20:50
 */
public class DecompilerUtil {
    private static LRUCache<String, String> decompileCache = new LRUCache<String, String>(100);

    public static String getDecompilerString(byte[] bytes, String className) {
        className = className.replace(".", "/");

        if (decompileCache.isContainsKey(className)) return decompileCache.get(className);

        DecompilerSettings settings = new DecompilerSettings();
        settings.setBytecodeOutputOptions(BytecodeOutputOptions.createVerbose());
        if (settings.getJavaFormattingOptions() == null) {
            settings.setJavaFormattingOptions(JavaFormattingOptions.createDefault());
        }
        settings.setShowDebugLineNumbers(true);
        DecompilationOptions decompilationOptions = new DecompilationOptions();
        decompilationOptions.setSettings(settings);
        decompilationOptions.setFullDecompilation(true);
        ArrayTypeLoader typeLoader = new ArrayTypeLoader(bytes);
        MetadataSystem metadataSystem = new MetadataSystem(typeLoader);
        TypeReference type = metadataSystem.lookupType(className);
        DecompilerProvider newProvider = new DecompilerProvider();
        newProvider.setDecompilerReferences(settings, decompilationOptions);
        newProvider.setType(type.resolve());
        newProvider.generateContent();
        String textContent = newProvider.getTextContent();

        if (textContent != null) decompileCache.put(className, textContent);
        return textContent;
    }

    public static String matchStringByRegularExpression(String line) {
        String regex = ".*\\/\\*[E|S]L:[0-9]*\\*\\/.*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        if (m.find()) {
            return m.group().replaceAll("\\/\\*[E|S]L:[0-9]*\\*\\/", "");
        }
        return line;
    }

}
