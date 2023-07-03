package com.hyunprise.backend.domain.coupon.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class CouponGenerateController {
    /*@RequestMapping("qr.do")
    public String makeqr(HttpServletRequest request, HttpSession session, String storeName) throws WriterException, IOException {

        String root = request.getSession().getServletContext().getRealPath("resources"); //현재 서비스가 돌아가고 있는 서블릿 경로의 resources 폴더 찾기

        String savePath = root + "\\qrCodes\\"; // 파일 경로

        //파일 경로가 없으면 생성하기
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 링크로 할 URL주소
        String url = "localhost:8800/jhpay/enterStore.do?store=" + storeName;

        // 링크 생성값
        String codeurl = new String(url.getBytes("UTF-8"), "ISO-8859-1");

        // QRCode 색상값
        int qrcodeColor = 0xFF2e4e96;
        // QRCode 배경색상값
        int backgroundColor = 0xFFFFFFFF;

        //QRCode 생성
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(codeurl, BarcodeFormat.QR_CODE, 200, 200);    // 200,200은 width, height

        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor, backgroundColor);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);

        //파일 이름에 저장한 날짜를 포함해주기 위해 date생성
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date()) + storeName;

        //파일 경로, 파일 이름 , 파일 확장자에 맡는 파일 생성
        File temp = new File(savePath + fileName + ".png");

        // ImageIO를 사용하여 파일쓰기
        ImageIO.write(bufferedImage, "png", temp);

        //리턴은 사용자가 원하는 값을 리턴한다.
        //작성자는 QRCode 파일의 이름을 넘겨주고 싶었음.
        return fileName + ".png";
    }*/
}