import AuthorProfile from '../components/author/AuthorProfile'
import ManuscriptList from '../components/author/ManuscriptList'

function AuthorPage() {
  return (
    <div style={{maxWidth: '1200px', margin: '140px auto'}}>
      <AuthorProfile />
      <ManuscriptList />
    </div>
  )
}

export default AuthorPage 