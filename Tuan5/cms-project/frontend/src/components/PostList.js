import React from 'react';

export default function PostList({ posts, onEdit, onDelete, onPublish }) {
  if (posts.length === 0) {
    return (
      <div style={styles.empty}>
        <div style={styles.emptyIcon}>📝</div>
        <p style={styles.emptyText}>No posts yet. Create your first post!</p>
      </div>
    );
  }

  return (
    <div style={styles.grid}>
      {posts.map(post => (
        <div key={post.id} style={styles.card}>
          <div style={styles.cardHeader}>
            <span style={{ ...styles.badge, ...(post.status === 'PUBLISHED' ? styles.badgePublished : styles.badgeDraft) }}>
              {post.status}
            </span>
            <span style={styles.date}>
              {post.createdAt ? new Date(post.createdAt).toLocaleDateString() : ''}
            </span>
          </div>
          <h3 style={styles.title}>{post.title}</h3>
          <p style={styles.content}>{post.content ? post.content.substring(0, 120) + (post.content.length > 120 ? '...' : '') : 'No content'}</p>
          <div style={styles.actions}>
            {post.status === 'DRAFT' && (
              <button onClick={() => onPublish(post.id)} style={styles.publishBtn}>Publish</button>
            )}
            <button onClick={() => onEdit(post)} style={styles.editBtn}>Edit</button>
            <button onClick={() => onDelete(post.id)} style={styles.deleteBtn}>Delete</button>
          </div>
        </div>
      ))}
    </div>
  );
}

const styles = {
  grid: { display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: 20 },
  card: { background: '#fff', borderRadius: 12, padding: 20, boxShadow: '0 2px 12px rgba(0,0,0,0.08)', border: '1px solid #f0f0f0', display: 'flex', flexDirection: 'column', gap: 12 },
  cardHeader: { display: 'flex', justifyContent: 'space-between', alignItems: 'center' },
  badge: { padding: '3px 10px', borderRadius: 20, fontSize: 12, fontWeight: 700, textTransform: 'uppercase', letterSpacing: 0.5 },
  badgePublished: { background: '#e6f9f0', color: '#1a9e5c' },
  badgeDraft: { background: '#fff3e0', color: '#e67e22' },
  date: { fontSize: 12, color: '#999' },
  title: { margin: 0, fontSize: 16, fontWeight: 700, color: '#1a1a2e', lineHeight: 1.4 },
  content: { margin: 0, fontSize: 14, color: '#666', lineHeight: 1.6, flexGrow: 1 },
  actions: { display: 'flex', gap: 8, marginTop: 4 },
  publishBtn: { flex: 1, padding: '7px 0', background: '#e6f9f0', color: '#1a9e5c', border: 'none', borderRadius: 8, cursor: 'pointer', fontSize: 13, fontWeight: 600 },
  editBtn: { flex: 1, padding: '7px 0', background: '#ede9ff', color: '#6c63ff', border: 'none', borderRadius: 8, cursor: 'pointer', fontSize: 13, fontWeight: 600 },
  deleteBtn: { flex: 1, padding: '7px 0', background: '#fff0f0', color: '#e74c3c', border: 'none', borderRadius: 8, cursor: 'pointer', fontSize: 13, fontWeight: 600 },
  empty: { textAlign: 'center', padding: 60 },
  emptyIcon: { fontSize: 48, marginBottom: 12 },
  emptyText: { color: '#999', fontSize: 16 },
};
