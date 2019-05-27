package br.com.lucas.botmonitoria.domain.response;

import java.io.Serializable;

import br.com.lucas.botmonitoria.domain.entity.Host;

public class PageHostsResponse extends PageResponse<Host> implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageHostsResponse(PageResponse p) {
		super(p.getNumber(), p.size, p.getTotalPages(), p.numberOfElements, p.totalElements, p.hasContent, p.first,
				p.last, p.nextPage, p.previousPage, p.content);
	}

}
