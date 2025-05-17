package rede.social.nester.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import rede.social.nester.exceptions.BadRequestBussinessException;

@Service
public class FotoService {

	private static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/jpeg", "image/jpg");
	private static final String JPG_EXTENSION_REGEX = ".+\\.(jpe?g)$";

	public void verificaSeFotoJPG(MultipartFile file) {
		String contentType = file.getContentType();
		if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
			throw new BadRequestBussinessException("Apenas arquivos JPG são permitidos (Content-Type inválido)");
		}
		String originalName = file.getOriginalFilename();
		if (originalName == null || !originalName.toLowerCase().matches(JPG_EXTENSION_REGEX)) {
			throw new BadRequestBussinessException("Extensão de arquivo inválida. Só JPG permitidos");
		}
		try (InputStream is = file.getInputStream()) {
			byte[] header = new byte[3];
			int lidos = is.read(header);
			if (lidos != 3 || !isJpegHeader(header)) {
				throw new BadRequestBussinessException("Arquivo não parece ser um JPEG válido (assinatura incorreta)");
			}
		} catch (IOException e) {
			throw new BadRequestBussinessException("Erro ao ler o arquivo de foto");
		}
	}

	private boolean isJpegHeader(byte[] header) {
		return (header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8 && (header[2] & 0xFF) == 0xFF;
	}
}
