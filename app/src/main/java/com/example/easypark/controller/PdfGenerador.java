package com.example.easypark.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;

import com.example.easypark.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import android.graphics.pdf.PdfDocument;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PdfGenerador {
    public static void createPdf(Context context, List<Registro> registros) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        // Configuración de estilo para el título
        Paint titlePaint = new Paint();
        titlePaint.setTextSize(24);
        titlePaint.setFakeBoldText(true);
        titlePaint.setColor(Color.BLACK);

        // Configuración de estilo para encabezados
        Paint headerPaint = new Paint();
        headerPaint.setTextSize(18);
        headerPaint.setFakeBoldText(true);
        headerPaint.setColor(Color.DKGRAY);

        // Configuración general de texto
        Paint textPaint = new Paint();
        textPaint.setTextSize(16);

        int pageWidth = 595; //ancho
        int pageHeight = 842; //altura
        int lineHeight = 40; // Altura de línea
        int margin = 20; // Margen izquierdo
        int startY; // Variable dinámica para la posición vertical

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Agregar logo
        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo); // Llamamos al logo que esta en la carpeta Drawable
        int logoWidth = 100; // Ancho del logo
        int logoHeight = 100; // Alto del logo
        int logoX = pageWidth / 2 - logoWidth / 2; // Centrar horizontalmente
        int logoY = 20; // Margen superior
        canvas.drawBitmap(Bitmap.createScaledBitmap(logo, logoWidth, logoHeight, false), logoX, logoY, paint);

        // Dibujar título debajo del logo
        startY = logoY + logoHeight + 20; // Ajustar la posición vertical después del logo
        canvas.drawText("Reporte de Registros", pageWidth / 2f - 100, startY, titlePaint);

        // Dibujar encabezados debajo del título
        startY += 40; // Ajustar espacio después del título
        canvas.drawText("ID", margin, startY, headerPaint);
        canvas.drawText("Fecha", margin + 100, startY, headerPaint);
        canvas.drawText("Precio", margin + 300, startY, headerPaint);

        startY += lineHeight; // Ajustar espacio para la primera fila de datos

        for (Registro registro : registros) {
            // Obtener datos del registro
            String fechaFormatted = registro.getFechaFormatted();

            // Dibujar datos del registro
            canvas.drawText(String.valueOf(registro.getCodigo()), margin, startY, textPaint);
            canvas.drawText(fechaFormatted, margin + 100, startY, textPaint);
            canvas.drawText(String.valueOf(registro.getPrecio()), margin + 300, startY, textPaint);

            // Dibujar línea debajo de la fila
            canvas.drawLine(margin, startY + 5, pageWidth - margin, startY + 5, textPaint);

            startY += lineHeight;
        }

        pdfDocument.finishPage(page);

        // Crear directorio
        File directory = new File(context.getExternalFilesDir(null), "EasyParkReports");
        if (!directory.exists() && !directory.mkdirs()) {
            Log.e("PdfGenerador", "Error al crear el directorio: " + directory.getPath());
            return;
        }

        String filePath = new File(directory, "reporte.pdf").getPath();
        Log.d("PdfGenerador", "Guardando PDF en la ruta: " + filePath); //filePath devuelve la ruta donde se creo el directorio

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            pdfDocument.writeTo(fos);
            Toast.makeText(context, "PDF creado en: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("PdfGenerador", "Error al crear el PDF: " + e.getMessage()); //maneja las excepciones en caso de error
        } finally {
            pdfDocument.close();
        }
    }
}

