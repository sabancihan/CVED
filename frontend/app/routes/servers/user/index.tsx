import { Link } from "@remix-run/react";

export default function AdminIndex() {
  return (
    <p>
      <Link to="new" title="Yeni Sunucu Oluştur">
      <svg  xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 mx-1 h-6 text-white">
        <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v6m3-3H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>

      </Link>
    </p>
  );
}