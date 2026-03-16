import React, { useState } from 'react';
import PostList from './components/PostList';
import PostForm from './components/PostForm';
import PluginPanel from './components/PluginPanel';
import { usePosts } from './hooks/usePosts';

export default function App() {
  const { posts, loading, error, createPost, updatePost, publishPost, deletePost } = usePosts();
  const [showForm, setShowForm] = useState(false);
  const [editingPost, setEditingPost] = useState(null);
  const [search, setSearch] = useState('');
  const [tab, setTab] = useState('all'); // all | published

  const filtered = posts
    .filter(p => tab === 'published' ? p.status === 'PUBLISHED' : true)
    .filter(p => p.title.toLowerCase().includes(search.toLowerCase()));

  const handleSubmit = async (data) => {
    if (editingPost) {
      await updatePost(editingPost.id, data);
    } else {
      await createPost(data);
    }
    setShowForm(false);
    setEditingPost(null);
  };

  const handleEdit = (post) => {
    setEditingPost(post);
    setShowForm(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Delete this post?')) await deletePost(id);
  };

  const stats = {
    total: posts.length,
    published: posts.filter(p => p.status === 'PUBLISHED').length,
    draft: posts.filter(p => p.status === 'DRAFT').length,
  };

  return (
    <div style={styles.app}>
      {/* Sidebar */}
      <aside style={styles.sidebar}>
        <div style={styles.logo}>
          <span style={styles.logoIcon}>⚡</span>
          <span style={styles.logoText}>MicroCMS</span>
        </div>

        <nav style={styles.nav}>
          <button onClick={() => setTab('all')} style={{ ...styles.navItem, ...(tab === 'all' ? styles.navActive : {}) }}>
            📄 All Posts
          </button>
          <button onClick={() => setTab('published')} style={{ ...styles.navItem, ...(tab === 'published' ? styles.navActive : {}) }}>
            ✅ Published
          </button>
        </nav>

        <div style={styles.stats}>
          <div style={styles.stat}><span style={styles.statNum}>{stats.total}</span><span style={styles.statLabel}>Total</span></div>
          <div style={styles.stat}><span style={{ ...styles.statNum, color: '#1a9e5c' }}>{stats.published}</span><span style={styles.statLabel}>Published</span></div>
          <div style={styles.stat}><span style={{ ...styles.statNum, color: '#e67e22' }}>{stats.draft}</span><span style={styles.statLabel}>Drafts</span></div>
        </div>

        <div style={{ marginTop: 'auto' }}>
          <PluginPanel />
        </div>
      </aside>

      {/* Main content */}
      <main style={styles.main}>
        <div style={styles.topbar}>
          <div>
            <h1 style={styles.pageTitle}>{tab === 'published' ? 'Published Posts' : 'All Posts'}</h1>
            <p style={styles.pageSubtitle}>{filtered.length} post{filtered.length !== 1 ? 's' : ''}</p>
          </div>
          <div style={styles.topbarRight}>
            <input
              value={search}
              onChange={e => setSearch(e.target.value)}
              placeholder="Search posts..."
              style={styles.searchInput}
            />
            <button onClick={() => { setEditingPost(null); setShowForm(true); }} style={styles.newBtn}>
              + New Post
            </button>
          </div>
        </div>

        {error && <div style={styles.error}>⚠️ {error}</div>}
        {loading ? (
          <div style={styles.loadingState}>Loading posts...</div>
        ) : (
          <PostList posts={filtered} onEdit={handleEdit} onDelete={handleDelete} onPublish={publishPost} />
        )}
      </main>

      {showForm && (
        <PostForm
          initial={editingPost}
          onSubmit={handleSubmit}
          onCancel={() => { setShowForm(false); setEditingPost(null); }}
        />
      )}
    </div>
  );
}

const styles = {
  app: { display: 'flex', minHeight: '100vh', background: '#f7f8fc', fontFamily: "'Inter', -apple-system, sans-serif" },
  sidebar: { width: 240, background: '#1a1a2e', color: '#fff', padding: 24, display: 'flex', flexDirection: 'column', gap: 24, flexShrink: 0 },
  logo: { display: 'flex', alignItems: 'center', gap: 10 },
  logoIcon: { fontSize: 24 },
  logoText: { fontSize: 20, fontWeight: 800, letterSpacing: -0.5 },
  nav: { display: 'flex', flexDirection: 'column', gap: 4 },
  navItem: { background: 'transparent', border: 'none', color: '#aaa', padding: '10px 14px', borderRadius: 8, cursor: 'pointer', fontSize: 14, textAlign: 'left', fontWeight: 500 },
  navActive: { background: 'rgba(108,99,255,0.3)', color: '#fff' },
  stats: { display: 'flex', gap: 8 },
  stat: { flex: 1, background: 'rgba(255,255,255,0.07)', borderRadius: 8, padding: '10px 8px', textAlign: 'center', display: 'flex', flexDirection: 'column', gap: 2 },
  statNum: { fontSize: 20, fontWeight: 800 },
  statLabel: { fontSize: 10, color: '#aaa', textTransform: 'uppercase', letterSpacing: 0.5 },
  main: { flex: 1, padding: 32, overflowY: 'auto' },
  topbar: { display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: 28 },
  pageTitle: { margin: 0, fontSize: 26, fontWeight: 800, color: '#1a1a2e' },
  pageSubtitle: { margin: '4px 0 0', fontSize: 14, color: '#999' },
  topbarRight: { display: 'flex', gap: 12, alignItems: 'center' },
  searchInput: { padding: '10px 14px', border: '1px solid #e0e0e0', borderRadius: 8, fontSize: 14, outline: 'none', width: 200, background: '#fff' },
  newBtn: { padding: '10px 20px', background: '#6c63ff', color: '#fff', border: 'none', borderRadius: 8, cursor: 'pointer', fontSize: 14, fontWeight: 700 },
  error: { background: '#fff0f0', border: '1px solid #f5c6cb', borderRadius: 8, padding: '12px 16px', color: '#c0392b', marginBottom: 20 },
  loadingState: { textAlign: 'center', padding: 60, color: '#999', fontSize: 16 },
};
