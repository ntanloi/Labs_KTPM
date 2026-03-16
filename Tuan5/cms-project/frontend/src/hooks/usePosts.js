import { useState, useEffect, useCallback } from 'react';
import { postService } from '../services/api';

export function usePosts() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchPosts = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await postService.getAll();
      setPosts(res.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { fetchPosts(); }, [fetchPosts]);

  const createPost = async (data) => {
    const res = await postService.create(data);
    setPosts(prev => [res.data, ...prev]);
    return res.data;
  };

  const updatePost = async (id, data) => {
    const res = await postService.update(id, data);
    setPosts(prev => prev.map(p => p.id === id ? res.data : p));
    return res.data;
  };

  const publishPost = async (id) => {
    const res = await postService.publish(id);
    setPosts(prev => prev.map(p => p.id === id ? res.data : p));
    return res.data;
  };

  const deletePost = async (id) => {
    await postService.delete(id);
    setPosts(prev => prev.filter(p => p.id !== id));
  };

  return { posts, loading, error, fetchPosts, createPost, updatePost, publishPost, deletePost };
}
