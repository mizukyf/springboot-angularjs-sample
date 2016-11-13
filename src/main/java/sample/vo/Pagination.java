package sample.vo;

import java.util.List;

public final class Pagination {
	private int page = 1;
	private int size = 25;
	public final int getPage() {
		return page;
	}
	public final void setPage(int page) {
		this.page = page;
	}
	public final int getSize() {
		return size;
	}
	public final void setSize(int size) {
		this.size = size;
	}
	
	public PaginateDataBuilder bindTotalCount(int c) {
		return new PaginateDataBuilder(this, c);
	}
	
	public static final class PaginateDataBuilder {
		private final Pagination p;
		private final int c;
		private PaginateDataBuilder(final Pagination p, final int c) {
			this.p = p;
			this.c = c;
		}
		public<T> Paginated<T> andFetchedItems(List<T> items) {
			return new Paginated<T>(p, items, c);
		}
	}
}
