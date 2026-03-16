import React, { useState, useEffect } from 'react';
import { pluginService } from '../services/api';

export default function PluginPanel() {
  const [plugins, setPlugins] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    pluginService.getAll()
      .then(res => setPlugins(res.data))
      .catch(() => setPlugins([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div style={styles.panel}>
      <h3 style={styles.heading}>🔌 Active Plugins</h3>
      {loading ? (
        <p style={styles.loading}>Loading plugins...</p>
      ) : plugins.length === 0 ? (
        <p style={styles.empty}>No plugins loaded.</p>
      ) : (
        <ul style={styles.list}>
          {plugins.map(p => (
            <li key={p.name} style={styles.item}>
              <span style={styles.name}>{p.name}</span>
              <span style={styles.version}>v{p.version}</span>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

const styles = {
  panel: { background: '#1a1a2e', borderRadius: 12, padding: 20, color: '#fff' },
  heading: { margin: '0 0 16px', fontSize: 15, fontWeight: 700 },
  loading: { color: '#aaa', fontSize: 13 },
  empty: { color: '#aaa', fontSize: 13 },
  list: { listStyle: 'none', margin: 0, padding: 0, display: 'flex', flexDirection: 'column', gap: 8 },
  item: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', background: 'rgba(255,255,255,0.07)', padding: '8px 12px', borderRadius: 8 },
  name: { fontSize: 13, fontWeight: 600 },
  version: { fontSize: 11, color: '#aaa', background: 'rgba(255,255,255,0.1)', padding: '2px 8px', borderRadius: 10 },
};
