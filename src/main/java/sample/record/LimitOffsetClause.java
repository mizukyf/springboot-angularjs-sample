package sample.record;

import sample.vo.Pagination;

public final class LimitOffsetClause {
	public static final LimitOffsetClause ALL = new LimitOffsetClause(0, 0);
	public static LimitOffsetClause of(final int limit, final int offset) {
		return new LimitOffsetClause(limit, offset);
	}
	public static LimitOffsetClause of(final Pagination paging) {
		final int limit = paging.getSize();
		final int offset = (paging.getPage() - 1) * limit;
		return new LimitOffsetClause(limit, offset);
	}
	public static LimitOffsetClause ofLimit(final int limit) {
		return new LimitOffsetClause(limit, 0);
	}
	public static LimitOffsetClause ofOffset(final int offset) {
		return new LimitOffsetClause(0, offset);
	}
	
	private final int limit;
	private final int offset;
	private LimitOffsetClause(final int limit, final int offset) {
		this.limit = limit;
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public int getOffset() {
		return offset;
	}
	public LimitOffsetClause andLimit(final int limit) {
		return new LimitOffsetClause(limit, this.offset);
	}
	public LimitOffsetClause andOffset(final int offset) {
		return new LimitOffsetClause(this.limit, offset);
	}
	@Override
	public String toString() {
		if (limit < 1 && offset < 1) {
			return " ";
		} else if (limit < 1) {
			return " OFFSET " + offset + ' '; 
		} else if (offset < 1) {
			return " LIMIT " + limit + ' '; 
		} else {
			return " LIMIT " + limit + " OFFSET " + offset + ' ';
		}
	}
}
