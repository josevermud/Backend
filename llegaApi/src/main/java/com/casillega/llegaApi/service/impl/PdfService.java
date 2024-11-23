package com.casillega.llegaApi.service.impl;

import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.repositories.AppUserRepository;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    private final AppUserRepository appUserRepository;

    public PdfService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public byte[] generateUserProfilePdf(Long userId) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // Get user data
            AppUser user = appUserRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Initialize PDF writer
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

            // Add content to the PDF
            document.add(new Paragraph("User Profile Information").setBold().setFontSize(16));
            document.add(new Paragraph("Full Name: " + user.getFullName()));
            document.add(new Paragraph("Username: " + user.getUserName()));
            document.add(new Paragraph("Email: " + user.getUserEmail()));
            document.add(new Paragraph("Role: " + user.getRole()));

            // Optionally add an image (e.g., profile picture)


            document.close();

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }
}
