package com.wmy.community.util;

import com.sun.deploy.uitoolkit.impl.awt.AWTDragHelper;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 敏感词过滤工具类
 * @Author: 三水
 * @Date: 2022/2/15 21:32
 */
@Component
public class SensitiveFilter {
    //敏感词替换符
    private static final String REPLACEMENT="***";

    //根节点
    private TrieNode rootNode=new TrieNode();

    //前缀树节点数据结构
    private class TrieNode{
        //关键字结束标识
        private boolean isKeywordEnd=false;

        //子节点
        private Map<Character,TrieNode> subNodes=new HashMap<>();

        public boolean isKeywordEnd(){
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd){
            isKeywordEnd=keywordEnd;
        }

        //添加子节点
        public void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }

        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }

    //将一个敏感词加入到前缀树中 用于初始化前缀树
    private void addKeyword(String keyword){
        TrieNode tempNode=rootNode;
        for(int i=0;i<keyword.length();i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if(subNode==null){
                //初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }

            tempNode=subNode;

            if(i==keyword.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    //根据敏感词文件初始化前缀树
    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                // 添加到前缀树
                this.addKeyword(keyword);
            }
        } catch (IOException e) {

        }
    }

    /**
     * 使用前缀树实现敏感词过滤
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return text;
        }

        //指针1
        TrieNode tempNode=rootNode;
        //指针2
        int begin=0;
        //指针3
        int position=0;
        //过滤结果
        StringBuilder sb = new StringBuilder();

        while (begin<text.length()){
            char c = text.charAt(position);
            //如果是符号，非汉字，跳过该字
            if(isSymbol(c)){
                //还处于根节点
                if(tempNode==rootNode){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            //检查下一级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode==null){
                sb.append(text.charAt(begin));
                begin++;
                position=begin;
                tempNode=rootNode;
            }else if(tempNode.isKeywordEnd()){
                sb.append(REPLACEMENT);
                position++;
                begin=position;
                tempNode=rootNode;
            }else {
                position++;
            }
        }

        return sb.toString();
    }

    //判断是否是符号，非汉字
    private boolean isSymbol(Character c){
        return !CharUtils.isAsciiAlphanumeric(c)&&(c<0x2E80||c>0x9FFF);
    }

}
