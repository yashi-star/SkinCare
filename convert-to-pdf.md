# How to Convert Documentation to PDF

## Method 1: Using Pandoc (Recommended)

### Install Pandoc

**Windows:**
1. Download from: https://github.com/jgm/pandoc/releases
2. Install the installer
3. Add to PATH

**macOS:**
```bash
brew install pandoc
brew install --cask basictex
```

**Linux:**
```bash
sudo apt-get install pandoc texlive-latex-base
```

### Convert to PDF

```bash
# Basic conversion
pandoc PROJECT_DOCUMENTATION.md -o PROJECT_DOCUMENTATION.pdf

# With better formatting
pandoc PROJECT_DOCUMENTATION.md -o PROJECT_DOCUMENTATION.pdf \
  --pdf-engine=xelatex \
  -V geometry:margin=1in \
  -V fontsize=11pt \
  --toc

# With custom styling
pandoc PROJECT_DOCUMENTATION.md -o PROJECT_DOCUMENTATION.pdf \
  --pdf-engine=xelatex \
  -V geometry:margin=1in \
  -V fontsize=11pt \
  -V colorlinks=true \
  -V linkcolor=blue \
  --toc \
  --highlight-style=tango
```

## Method 2: Online Tools

### Option A: Dillinger.io
1. Go to https://dillinger.io/
2. Copy content from `PROJECT_DOCUMENTATION.md`
3. Paste into editor
4. Click "Export as" → "PDF"

### Option B: Markdown to PDF
1. Go to https://www.markdowntopdf.com/
2. Upload `PROJECT_DOCUMENTATION.md`
3. Click "Convert to PDF"
4. Download the PDF

### Option C: StackEdit
1. Go to https://stackedit.io/
2. Import `PROJECT_DOCUMENTATION.md`
3. Click "Export" → "PDF"

## Method 3: VS Code Extension

1. Install "Markdown PDF" extension in VS Code
2. Open `PROJECT_DOCUMENTATION.md`
3. Right-click → "Markdown PDF: Export (pdf)"
4. PDF will be generated in the same directory

## Method 4: Using Python (markdown2pdf)

```bash
# Install
pip install markdown2pdf

# Convert
markdown2pdf PROJECT_DOCUMENTATION.md
```

## Method 5: Using Node.js (md-to-pdf)

```bash
# Install
npm install -g md-to-pdf

# Convert
md-to-pdf PROJECT_DOCUMENTATION.md
```

## Recommended Command

For best results, use this Pandoc command:

```bash
pandoc PROJECT_DOCUMENTATION.md -o PROJECT_DOCUMENTATION.pdf \
  --pdf-engine=xelatex \
  -V geometry:margin=1in \
  -V fontsize=11pt \
  -V colorlinks=true \
  -V linkcolor=blue \
  -V urlcolor=blue \
  --toc \
  --toc-depth=3 \
  --highlight-style=tango \
  -V mainfont="Times New Roman" \
  -V monofont="Courier New"
```

This will create a professional PDF with:
- Table of contents
- Proper margins
- Clickable links
- Syntax highlighting
- Professional fonts

