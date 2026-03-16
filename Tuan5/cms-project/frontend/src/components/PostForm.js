import React, { useState, useEffect } from 'react';

export default function PostForm({ initial, onSubmit, onCancel }) {
  const [form, setForm] = useState({ title: '', content: '', status: 'DRAFT' });

  useEffect(() => {
    if (initial) setForm({ title: initial.title, content: initial.content || '', status: initial.status });
  }, [initial]);

  const handleChange = (e) => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!form.title.trim()) return alert('Title is required');
    onSubmit(form);
  };

  return (
    <div style={styles.overlay}>
      <div style={styles.modal}>
        <h2 style={styles.heading}>{initial ? 'Edit Post' : 'New Post'}</h2>
        <form onSubmit={handleSubmit}>
          <div style={styles.field}>
            <label style={styles.label}>Title *</label>
            <input
              name="title"
              value={form.title}
              onChange={handleChange}
              style={styles.input}
              placeholder="Enter post title..."
            />
          </div>
          <div style={styles.field}>
            <label style={styles.label}>Content</label>
            <textarea
              name="content"
              value={form.content}
              onChange={handleChange}
              rows={8}
              style={{ ...styles.input, resize: 'vertical' }}
              placeholder="Write your content here..."
            />
          </div>
          <div style={styles.field}>
            <label style={styles.label}>Status</label>
            <select name="status" value={form.status} onChange={handleChange} style={styles.input}>
              <option value="DRAFT">Draft</option>
              <option value="PUBLISHED">Published</option>
            </select>
          </div>
          <div style={styles.actions}>
            <button type="button" onClick={onCancel} style={styles.cancelBtn}>Cancel</button>
            <button type="submit" style={styles.submitBtn}>{initial ? 'Update' : 'Create'}</button>
          </div>
        </form>
      </div>
    </div>
  );
}

const styles = {
  overlay: { position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.5)', display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 1000 },
  modal: { background: '#fff', borderRadius: 12, padding: 32, width: '100%', maxWidth: 560, boxShadow: '0 20px 60px rgba(0,0,0,0.2)' },
  heading: { margin: '0 0 24px', fontSize: 22, color: '#1a1a2e' },
  field: { marginBottom: 16 },
  label: { display: 'block', marginBottom: 6, fontWeight: 600, fontSize: 14, color: '#444' },
  input: { width: '100%', padding: '10px 12px', border: '1px solid #ddd', borderRadius: 8, fontSize: 14, boxSizing: 'border-box', outline: 'none' },
  actions: { display: 'flex', gap: 12, justifyContent: 'flex-end', marginTop: 24 },
  cancelBtn: { padding: '10px 20px', border: '1px solid #ddd', borderRadius: 8, background: '#fff', cursor: 'pointer', fontSize: 14 },
  submitBtn: { padding: '10px 24px', background: '#6c63ff', color: '#fff', border: 'none', borderRadius: 8, cursor: 'pointer', fontSize: 14, fontWeight: 600 },
};
