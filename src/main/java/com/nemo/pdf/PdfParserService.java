package com.nemo.pdf;

import com.alibaba.fastjson.JSONArray;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.ToHTMLContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mingLong.zhao
 * PDF提取器
 */
public class PdfParserService {
    public static void main(String[] args) throws Exception {
        String path = PdfParserService.class.getClassLoader().getResource("").getPath();
        String filePath = path
            + "/templates/千禾味业：千禾味业食品股份有限公司和招商证券股份有限公司关于千禾味业食品股份有限公司非公开发行A股股票申请文件反馈意见的回复.pdf";
        PdfParserService pdfParserService = new PdfParserService();
        // 解析文字
        //pdfParserService.getText(filePath);
        // 解析图片
        //pdfParserService.pdfImageParser(filePath);
        // 处理表格
        //pdfParserService.pdfTableParser(filePath);
        // 转成html
        pdfParserService.pdfToHtmlConverter(filePath);
    }

    public void pdfTableParser(String pdfFilePath) {
        
    }

    public String pdfToHtmlConverter(String pdfFilePath) throws Exception {
        try {
            // 创建PDF解析器
            PDFParser parser = new PDFParser();
            InputStream pdfStream = new FileInputStream(new File(pdfFilePath));
            // 创建元数据对象
            Metadata metadata = new Metadata();
            // 创建HTML内容处理器，指定保留标记
            ToHTMLContentHandler handler = new ToHTMLContentHandler();
            // 解析PDF文件并将内容写入HTML处理器
            parser.parse(pdfStream, handler, metadata);
            // 输出HTML标记文本
            System.out.println(handler.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void pdfImageParser(String pdfFilePath) {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            List<PDImageXObject> images = new LinkedList<>();

            for (PDPage page : document.getPages()) {
                page.getResources().getXObjectNames().forEach(name -> {
                    try {
                        PDXObject xObject = page.getResources().getXObject(name);
                        if (xObject instanceof PDImageXObject) {
                            images.add((PDImageXObject)xObject);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                for (PDImageXObject image : images) {
                    System.out.println("Image width: " + image.getWidth());
                    System.out.println("Image height: " + image.getHeight());
                    System.out.println("Image format: " + image.getSuffix());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getText(String pathname) {
        try (PDDocument document = PDDocument.load(new File(pathname))) {
            // 解析文本
            /**
             * PDFTextStripper根据不同的文本段落分隔符将提取的文本分成不同的段落。默认情况下，PDFTextStripper使用以下几种分隔符：
             *
             * 换行符：PDFTextStripper将文本分割成多行。在PDF文档中，通常使用换行符表示一个新的段落或一个新的行。
             *
             * 段落结束符：PDFTextStripper使用段落结束符将文本分成不同的段落。在PDF文档中，通常使用多个空行表示一个新的段落。
             *
             * 制表符：PDFTextStripper使用制表符将文本分割成不同的列。在PDF文档中，通常使用制表符来对齐表格中的文本。
             *
             * PDFTextStripper还提供了一些方法，允许用户自定义分段规则。例如：
             *
             * setLineSeparator()方法可以设置自定义的换行符。
             *
             * setParagraphEnd()方法可以设置自定义的段落结束符。
             *
             * setWordSeparator()方法可以设置自定义的单词分隔符
             */
            List textList = new LinkedList();
            PDFTextStripper textStripper = new PDFTextStripper() {
                @Override
                protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                    textList.add(text);
                    // 处理每个字符
                    for (TextPosition textPosition : textPositions) {

                    }
                }
            };
            // 控制提取的文本是否按照位置进行排序
            textStripper.setSortByPosition(true);
            textStripper.setStartPage(0);
            textStripper.setEndPage(document.getNumberOfPages() - 1);
            String text = textStripper.getText(document);
            System.out.println("Text: " + text);
            System.out.println(JSONArray.toJSONString(textList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
