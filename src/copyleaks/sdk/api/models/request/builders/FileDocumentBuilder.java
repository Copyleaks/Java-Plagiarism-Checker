/********************************************************************************
 The MIT License(MIT)
 
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
********************************************************************************/

package copyleaks.sdk.api.models.request.builders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import copyleaks.sdk.api.models.request.FileDocument;
import copyleaks.sdk.api.models.request.ScanProperties;

/**
 * Creates a new FileDocument
 */
public class FileDocumentBuilder {
	private String base64;
	private String filename;
	private ScanProperties scanProperties;

	public FileDocumentBuilder() {
	}

	/**
	 * Add ScanProperties to the document
	 * 
	 * @param scanProperties
	 * @return FileDocumentBuilder
	 */
	public FileDocumentBuilder setScanProperties(ScanProperties scanProperties) {
		this.scanProperties = scanProperties;
		return this;
	}

	/**
	 * Add filePath to the document
	 * 
	 * @param filePath
	 * @return FileDocumentBuilder
	 * @throws IOException if the filePath cannot be read
	 */
	public FileDocumentBuilder setFilePath(String filePath) throws IOException {
		File file = new File(filePath);
		byte[] bytes = new byte[(int) file.length()];
		this.readFile(file).read(bytes);
		String fileEncodedBase64 = new String(Base64.getEncoder().encodeToString(bytes));
		this.base64 = fileEncodedBase64;
		return this;
	}

	/**
	 * Add filename to the document
	 * 
	 * @param filename
	 * @return FileDocumentBuilder
	 */
	public FileDocumentBuilder setFilename(String filename) {
		this.filename = filename;
		return this;
	}

	/**
	 * Build the FileDocument
	 * 
	 * @return FileDocument
	 */
	public FileDocument build() {
		if (this.base64 == null)
			throw new IllegalArgumentException("base64 must have a value");
		if (this.filename == null)
			throw new IllegalArgumentException("filename must have a value");
		if (this.scanProperties == null)
			throw new IllegalArgumentException("scanProperties must have a value");
		return new FileDocument(base64, filename, scanProperties);
	}

	private FileInputStream readFile(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}
}
