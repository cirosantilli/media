.PHONY: clean

multipage_refs.pdf: multipage_refs.tex
	pdflatex '$<'
	pdflatex '$<'

clean:
	rm -f *.aux *.log *.out *.pdf *.toc
