const API_BASE = import.meta.env.VITE_API_URL || 'http://localhost:8081';

export type Product = {
  id: number;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
};

export async function getProducts(): Promise<Product[]> {
  const res = await fetch(`${API_BASE}/api/products`, { cache: 'no-store' });
  if (!res.ok) throw new Error('Failed to load products');
  return res.json();
}

export async function sendContact(data: { name: string; email: string; message: string }) {
  const res = await fetch(`${API_BASE}/api/contact`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to send message');
  return true;
}